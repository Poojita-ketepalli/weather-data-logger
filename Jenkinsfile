pipeline {
    agent any

    environment {
        MYSQL_ROOT_PASSWORD = credentials('MYSQL_ROOT_PASSWORD')  // Use Jenkins credentials for security
        MYSQL_DATABASE = 'weatherdb'
        MYSQL_USER = 'root'
    }

    stages {
        stage('Checkout Code') {
            steps {
                git credentialsId: 'github-credentials', url: 'git@github.com:Poojita-ketepalli/weather-data-logger.git', branch: 'main'
            }
        }

        stage('Start MySQL') {
            steps {
                script {
                    sh '''
                    if systemctl is-active --quiet mysql; then
                        echo "MySQL is already running"
                    else
                        sudo systemctl start mysql || docker run --name mysql -e MYSQL_ROOT_PASSWORD=${MYSQL_ROOT_PASSWORD} -e MYSQL_DATABASE=${MYSQL_DATABASE} -p 3306:3306 -d mysql:8
                        sleep 20
                    fi
                    '''
                }
            }
        }

        stage('Test MySQL Connection') {
            steps {
                script {
                    sh '''
                    mysql --host=127.0.0.1 --user=root --password=${MYSQL_ROOT_PASSWORD} -e "SHOW DATABASES;"
                    '''
                }
            }
        }

        stage('Setup JDK 17') {
            steps {
                script {
                    sh 'java -version'
                }
            }
        }

        stage('Build with Maven') {
            steps {
                script {
                    sh 'mvn clean package'
                }
            }
        }

        stage('Run Tests') {
            steps {
                script {
                    sh 'mvn test'
                }
            }
        }

        stage('Archive JAR') {
            steps {
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
            }
        }
    }
}
