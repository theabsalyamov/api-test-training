tools {
    jdk 'JDK-17'
}

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
                sh 'chmod +x ./gradlew'
                // Принудительно отключаем toolchain
                sh './gradlew clean api-test:assemble -Dorg.gradle.java.installations.auto-detect=false'
            }
        }

        stage('Run API tests') {
            steps {
                sh './gradlew api-test:test -Dorg.gradle.java.installations.auto-detect=false'
            }
        }

        stage('Allure Report') {
            steps {
                allure includeProperties: false,
                       results: [[path: 'api-test/build/allure-results']]
            }
        }
    }
}

