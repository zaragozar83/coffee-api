pipeline {
    agent any
    
    tools{
        gradle 'Gradle-6.8.3'   
    }
    
    stages {
        stage("build") {
            steps {
                echo 'build the application...'
                sh 'gradle -v'
                echo 'run gradle clean build...'
                sh 'gradle clean build'
            }
        }

        stage("test") {
            steps{
                echo 'testing the application...'
                sh 'gradle test'
                // gradle test
            }
        }

        stage("deploy") {
            steps{
                echo 'deploy the application...'
            }
        }
    }
}
