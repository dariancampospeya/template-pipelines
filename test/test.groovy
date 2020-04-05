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
        
        //inject stage code
        stage('dev-pipeline') {
            steps {
                dir("test"){
                    script{
                        def data = readFile(file: '_preparation.groovy')
                        println(data)
                        {% include data with context %}
                    }

                    sh "ls -ls"
                    sh "pwd"
                    sh "echo My appname ${appname}"  
                }
            }
        }
    }

}