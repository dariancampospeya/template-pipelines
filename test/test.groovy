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
                    sh "ls -ls"
                    sh "pwd"
                    sh "echo My appname ${appname}"  
                }
            }
        }
        //inject stage code dev repo
        stage ('dev-file'){
            steps{
                dir("test"){
                    script {
                        def fileDev = readFile(file: '_preparation.groovy')
                        println(fileDev)
                        //{% include fileDev with context %}
                        new File(fileDev).eachLine { line -> 
                            println line 
                        }                     
                    }  
                }                 
            }
        }

    
    }

}