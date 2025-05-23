node {

    stage("checkout repo"){
        git branch: 'master',
            credentialsId: 'github-token',
            url: 'https://github.com/theabsalyamov/api-test-training.git'
    }

    stages("build") {
        sh "./gradlew clean api-test:assemble"
    }

    stages("run api test") {
        sh "./gradlew api-test:test"
    }

}

