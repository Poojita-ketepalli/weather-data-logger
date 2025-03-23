pipeline {
    agent any

    environment {
        DB_HOST = "mysql-container"  // ✅ Setting DB_HOST here
    }

    stages {
        stage('Checkout Code') {
            steps {
                script {
                    checkout([
                        $class: 'GitSCM',
                        branches: [[name: '*/main']],
                        userRemoteConfigs: [[
                            url: 'https://github.com/Poojita-ketepalli/weather-data-logger.git',
                            credentialsId: 'github-credentials'
                        ]]
                    ])
                }
            }
        }

        stage('Retrieve MySQL Credentials') {
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: 'mysql-credentials', usernameVariable: 'DB_USER', passwordVariable: 'DB_PASS')]) {
                        echo "✅ Retrieved MySQL credentials"
                    }
                }
            }
        }

        stage('Wait for MySQL') {
            steps {
                script {
                    def mysqlReady = sh(script: "until mysqladmin ping -h ${DB_HOST} --silent; do sleep 5; done", returnStatus: true)
                    if (mysqlReady != 0) {
                        error("❌ MySQL is not ready!")
                    } else {
                        echo "✅ MySQL is ready!"
                    }
                }
            }
        }

        stage('Build') {
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: 'mysql-credentials', usernameVariable: 'DB_USER', passwordVariable: 'DB_PASS')]) {
                        sh "mvn clean package -Dspring.datasource.url=jdbc:mysql://${DB_HOST}:3306/weather_db -Dspring.datasource.username=${DB_USER} -Dspring.datasource.password=${DB_PASS}"
                    }
                }
            }
        }

         stage('Run Tests') {   // ✅ New test stage added
                    steps {
                        script {
                            withCredentials([usernamePassword(credentialsId: 'mysql-credentials', usernameVariable: 'DB_USER', passwordVariable: 'DB_PASS')]) {
                                sh "mvn test -Dspring.datasource.url=jdbc:mysql://${DB_HOST}:3306/weather_db -Dspring.datasource.username=${DB_USER} -Dspring.datasource.password=${DB_PASS}"
                            }
                        }
                    }
                }

         stage('Archive Build Artifact') {  // ✅ New Stage to Store JAR
                     steps {
                         archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
                         echo "✅ JAR file archived successfully"
                     }
                 }
    }
}
