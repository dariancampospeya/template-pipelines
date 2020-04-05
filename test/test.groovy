pipeline {
    agent { label "master"}
   
    //add environment
    environment {
       appname = "${APPNAME}"
       tag = "${TAG}"
    }

    stages {
        stage('checkout'){
            steps {
                dir("${appname}"){
                    checkout([$class: 'GitSCM',
                    branches: [[name: "${tag}"]],
                    extensions: [[$class: 'CloneOption', timeout: 120]],
                    gitTool: 'Default',
                    userRemoteConfigs: [[url: "https://github.com/dariancampospeya/test.git"]]
                    ])
                }         
            }
        }
        stage('test') {
            steps {
                dir("test"){
                    script{
                        def data = readFile(file: '_preparation.groovy')
                        println(data)
                    }

                    sh "ls -ls"
                    sh "pwd"
                    sh "echo My appname ${appname}"  
                }
            }
        }
        //inject stage code dev repo
        GroovyShell shell = new GroovyShell()
        def Util = shell.parse(new File("test/_preparation.groovy"))
        Util.fetchData()

    }

}