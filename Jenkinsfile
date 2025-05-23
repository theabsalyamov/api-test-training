pipeline {
    agent any

    stages {
        stage('Checkout repo') {
            steps {
                git branch: 'main',
                    credentialsId: 'github-access-token',
                    url: 'https://github.com/theabsalyamov/api-test-training.git'
            }
        }

        stage('Build') {
            steps {
                sh './gradlew clean api-test:assemble'
            }
        }

        stage('Run API tests') {
            steps {
                sh './gradlew api-test:test'
            }
        }
    }
}


