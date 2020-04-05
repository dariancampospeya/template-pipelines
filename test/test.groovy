pipeline {
    agent { label "master"}
   
    //add environment
    environment {
       appname = "${APPNAME}"
       tag = "${TAG}"
       env_credential = "darian.campos"
    }

    stages {
        stage('checkout'){
            steps {
                dir("${appname}"){
                    checkout([$class: 'GitSCM',
                    branches: [[name: "${tag}"]],
                    extensions: [[$class: 'CloneOption', timeout: 120]],
                    gitTool: 'Default',
                    userRemoteConfigs: [[url: "git@github.com:dariancampospeya/test.git", credentialsId: "${env_credential}"]]
                    ])
                }         
            }
        }

        //{% include '${appname}/_preparation.groovy' with context %}

        
        //inject stage code
        stage('test') {
            steps {
                dir("${appname}/test"){
                    sh "ls -ls"
                    sh "pwd"
                    sh "echo My appname ${appname}"  
                }
            }
        }
    }

}