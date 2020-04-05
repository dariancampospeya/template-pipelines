pipeline {
    agent { label "master"}
   
    //add environment
    environment {
       appname = "${APPNAME}"
    }

        
        //inject stage code
        stage('test') {
            steps {
                sh "echo My appname ${appname}"  
            }
        }
    }

}