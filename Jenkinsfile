pipeline {
    agent { label 'local' }

    environment {
        REPO_URL = 'https://github.com/OtoGvazava/DemoServiceTest.git'
        IMAGE_NAME = 'demo-service-test'
        CONTAINER_NAME = 'demo-service-test'
        NETWORK_NAME = 'demo-net'
        TARGET_SERVICE_HOST = 'demo-service-container'
        TARGET_SERVICE_PORT = '8090'
    }

    stages {
        stage('Build Docker Image') {
            steps {
                sh "docker build -t ${IMAGE_NAME} ."
            }
        }

        stage('Run Test Container') {
            steps {
                sh """
                    docker run --rm \
                        --name ${CONTAINER_NAME} \
                        --network ${NETWORK_NAME} \
                        -e BASE_URL=http://${TARGET_SERVICE_HOST}:${TARGET_SERVICE_PORT} \
                        ${IMAGE_NAME}
                """
            }
        }
    }

    post {
        always {
            echo '🧪 DemoServiceTest pipeline finished.'
        }
        success {
            echo '✅ All integration tests passed.'
        }
        failure {
            echo '❌ One or more integration tests failed.'
        }
    }
}