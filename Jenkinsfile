pipeline {
    agent any

    environment {
        MYSQL_ROOT_PASSWORD = credentials('MYSQL_ROOT_PASSWORD') // Ensure this credential exists in Jenkins
        MYSQL_DATABASE = 'weatherdb'
    }

    stages {
        stage('Checkout Code') {
            steps {
                git branch: 'main', credentialsId: 'github-credentials', url: 'https://github.com/Poojita-ketepalli/weather-data-logger.git'
            }
        }

        











    }
}
