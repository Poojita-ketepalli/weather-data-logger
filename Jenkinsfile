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

        stage('Start MySQL') {
            steps {
                script {
                    echo '🚀 Starting MySQL...'

                    // Ensure MySQL container is started
                    sh '''
                    if ! docker ps | grep -q mysql; then
                        docker run --name mysql -e MYSQL_ROOT_PASSWORD=${MYSQL_ROOT_PASSWORD} -e MYSQL_DATABASE=${MYSQL_DATABASE} -p 3306:3306 -d mysql:8
                        sleep 10
                    fi
                    '''
                }
            }
        }

        