pipeline {
    agent any
    
    tools{
        gradle 'Gradle-6.8.3'   
    }
    
    stages {
        stage("build") {
            steps {
                echo 'build the application'
                sh 'gradle -v'
            }
        }

        stage("test") {
            steps{
                echo 'testing the application'
                // gradle test
            }
        }

        stage("deploy") {
            steps{
                echo 'deploy the application'
            }
        }
    }
}
