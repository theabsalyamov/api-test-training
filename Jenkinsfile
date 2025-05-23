node {

    stage("checkout repo"){
        git branch: 'main',
            credentialsId: 'github-access-token',
            url: 'https://github.com/theabsalyamov/api-test-training.git'
    }

    stages("build") {
        sh "./gradlew clean api-test:assemble"
    }

    stages("run api test") {
        sh "./gradlew api-test:test"
    }

}

