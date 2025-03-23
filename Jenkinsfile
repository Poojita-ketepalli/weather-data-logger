pipeline {
    agent any

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
    }
}
