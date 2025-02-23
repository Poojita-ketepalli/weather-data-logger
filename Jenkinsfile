pipeline {
    agent any

    environment {
        MYSQL_ROOT_PASSWORD = 'Puji2002@'
        MYSQL_DATABASE = 'weatherdb'
        MYSQL_USER = 'root'
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
                    sh '''
                    sudo systemctl start mysql || docker run --name mysql -e MYSQL_ROOT_PASSWORD=Puji2002@ -e MYSQL_DATABASE=weatherdb -p 3306:3306 -d mysql:8
                    sleep 20
                    '''
                }
            }
        }

        stage('Test MySQL Connection') {
            steps {
                script {
                    sh '''
                    mysql -h 127.0.0.1 -u root -pPuji2002@ -e "SHOW DATABASES;"
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
