pipeline {
    agent any
    stages {
        stage("build") {
            steps {
                echo 'build the application'
                withGradle(){
                    sh './gradlew -v'
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
