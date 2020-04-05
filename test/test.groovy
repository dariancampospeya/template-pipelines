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

        //{% include '${appname}/_preparation.groovy' with context %}

        
        //inject stage code
        stage('test') {
            steps {
                dir("test"){
                    sh "ls -ls"
                    sh "pwd"
                    sh "echo My appname ${appname}"  
                }
            }
        }
    }

}