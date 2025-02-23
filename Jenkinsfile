pipeline {
    agent any

    environment {
        MYSQL_ROOT_PASSWORD = 'Puji2002@'
        MYSQL_DATABASE = 'weatherdb'
    }

    stages {
        stage('Checkout Code') {
            steps {
                checkout([$class: 'GitSCM', branches: [[name: '*/main']],
                  userRemoteConfigs: [[
                    url: 'https://github.com/Poojita-ketepalli/weather-data-logger.git',
                    credentialsId: 'github-credentials'
                  ]]
                ])
            }
        }

        stage('Start MySQL') {
            steps {
                script {
                    sh '''
                    # Try to start MySQL if it's already installed
                    systemctl start mysql 2>/dev/null || echo "MySQL service not found, using Docker..."

                    # Check if MySQL is already running
                    if ! mysqladmin ping -h 127.0.0.1 --silent; then
                        # Start MySQL using Docker if it's not running
                        docker run --name mysql -e MYSQL_ROOT_PASSWORD=Puji2002@ -e MYSQL_DATABASE=weatherdb -p 3306:3306 -d mysql:8 || echo "Docker not found!"
                        sleep 20
                    else
                        echo "MySQL is already running."
                    fi
                    '''
                }
            }
        }

        stage('Test MySQL Connection') {
            steps {
                script {
                    sh '''
                    mysql -h 127.0.0.1 -u root -pPuji2002@ -e "SHOW DATABASES;" || echo "Failed to connect to MySQL!"
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
