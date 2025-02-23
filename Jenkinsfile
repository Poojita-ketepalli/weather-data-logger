pipeline {
    agent any

    environment {
        MYSQL_ROOT_PASSWORD = credentials('MYSQL_ROOT_PASSWORD')  // Secure MySQL password from Jenkins
        MYSQL_DATABASE = 'weatherdb'
        MYSQL_USER = 'root'
    }

    stages {
        stage('Checkout Code') {
            steps {
                git credentialsId: 'github-token',
                    url: 'https://github.com/Poojita-ketepalli/weather-data-logger.git',
                    branch: 'main'
            }
        }

        stage('Start MySQL') {
            steps {
                script {
                    sh '''
                    if systemctl is-active --quiet mysql; then
                        echo "✅ MySQL is already running"
                    else
                        echo "🚀 Starting MySQL..."
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
                    echo "🔄 Testing MySQL connection..."
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
