pipeline {
    agent any

    environment {
        DB_HOST = "mysql-container"  // ✅ Setting DB_HOST here
        EC2_USER = 'ubuntu'
        EC2_IP = '13.235.69.199'
        SSH_CREDENTIALS_ID = 'jenkins-ssh-key'   // The Jenkins credentials ID for SSH
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

         stage('Deploy Locally') {
             steps {
                 script {
                     sh '''
                         echo "🚀 Stopping old application..."
                         pkill -f 'weatherdata-0.0.1-SNAPSHOT.jar' || true

                         echo "🚀 Deploying new version..."
                         nohup java -jar /var/jenkins_home/workspace/weather-application-ci-cd/target/weatherdata-0.0.1-SNAPSHOT.jar --spring.datasource.url=jdbc:mysql://mysql-container:3306/weather_db --spring.datasource.username=$DB_USER --spring.datasource.password=$DB_PASS > app.log 2>&1 &

                         sleep 5  # Give some time for the app to start

                         echo "✅ Deployment complete!"
                         ps aux | grep java  # Verify if the process is running
                     '''
                 }
             }
         }
         stage('Deploy to EC2') {
             steps {
                 script {
                     def jarFile = "target/weatherdata-0.0.1-SNAPSHOT.jar"
                     def remotePath = "/home/ubuntu/app/weatherdata.jar"
                     def sshKey = "/var/jenkins_home/.ssh/id_rsa"

                     // ✅ Copy JAR file to EC2 using SCP
                     sh """
                         scp -o StrictHostKeyChecking=no -i ${sshKey} ${jarFile} ${EC2_USER}@${EC2_IP}:${remotePath}
                     """

                     // ✅ SSH into EC2 and restart application safely
                     sh """
                         ssh -o StrictHostKeyChecking=no -i ${sshKey} ${EC2_USER}@${EC2_IP} << 'EOF'
                         echo "Stopping existing application..."

                         # Find and kill the process safely
                         PID=\$(pgrep -f '${remotePath}')
                         if [ -n "\$PID" ]; then
                             echo "Killing process \$PID"
                             kill -9 \$PID
                         fi

                         echo "Starting new application..."
                         nohup java -jar ${remotePath} --server.port=6161 \\
                             --spring.datasource.url=jdbc:mysql://\${DB_HOST}:3306/weather_db \\
                             --spring.datasource.username=\${DB_USER} \\
                             --spring.datasource.password=\${DB_PASS} > /home/ubuntu/app.log 2>&1 &

                         echo "Application deployed successfully!"
                         
                     """
                 }
             }
         }
         }
}