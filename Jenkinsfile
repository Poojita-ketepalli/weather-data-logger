pipeline {
    agent any

    environment {
        MYSQL_ROOT_PASSWORD = credentials('mysql-root-password') // Use Jenkins credentials
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

                    // Ensure Docker is accessible and MySQL container is not already running
                    sh '''
                    if ! docker ps | grep -q mysql; then
                        docker run --name mysql -e MYSQL_ROOT_PASSWORD=${MYSQL_ROOT_PASSWORD} -e MYSQL_DATABASE=${MYSQL_DATABASE} -p 3306:3306 -d mysql:8
                        sleep 10  # Wait for MySQL to initialize
                    fi
                    '''
                }
            }
        }

        stage('Test MySQL Connection') {
            steps {
                script {
                    sh '''
                    for i in {1..10}; do
                        if mysql -h 127.0.0.1 -u root -p${MYSQL_ROOT_PASSWORD} -e "SHOW DATABASES;"; then
                            echo "✅ MySQL is running"
                            exit 0
                        fi
                        echo "⏳ Waiting for MySQL to be ready..."
                        sleep 5
                    done
                    echo "❌ MySQL did not start in time"
                    exit 1
                    '''
                }
            }
        }

        stage('Setup JDK 17') {
            steps {
                script {
                    sh 'java -version' // Verify JDK is installed
                }
            }
        }

        stage('Build with Maven') {
            steps {
                sh 'mvn clean package'
            }
        }

        stage('Run Tests') {
            steps {
                sh 'mvn test'
            }
        }

        stage('Archive JAR') {
            steps {
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
            }
        }
    }

    post {
        always {
            script {
                echo '🧹 Cleaning up...'
                sh 'docker stop mysql || true'
                sh 'docker rm mysql || true'
            }
        }
        success {
            echo '✅ Build succeeded!'
        }
        failure {
            echo '❌ Build failed!'
        }
    }
}
