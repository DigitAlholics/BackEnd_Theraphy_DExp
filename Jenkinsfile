pipeline {
    agent any
    environment {
        DOCKER_REGISTRY = 'docker.io'  // Reemplaza con tu registro de Docker
        KUBE_NAMESPACE = 'DigitAlholics2'  // Reemplaza con el espacio de nombres de Kubernetes
        KUBE_DEPLOYMENT = 'digitalholics2-deployment'  // Reemplaza con el nombre del despliegue en Kubernetes
        KUBE_SERVER = 'https://digitalholics2-dns-0l9ll47v.hcp.eastus.azmk8s.io:443'  // Reemplaza con las credenciales de Kubernetes
    }
    tools { 
        maven 'MAVEN_3_9_5'
        jdk 'jdk-17'
    }
	
    stages {
        stage ('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                withMaven(maven : 'MAVEN_3_9_5') {
                    bat 'mvn clean package' // Compila y empaqueta el proyecto
                }
            }
        }

        stage('Unit Tests') {
            steps {
                withMaven(maven : 'MAVEN_3_9_5') {
                    bat 'mvn test'  // Ejecuta pruebas unitarias
                }
            }
        }

        stage('Integration Tests') {
            steps {
                withMaven(maven : 'MAVEN_3_9_5') {
                    bat 'mvn verify'  // Ejecuta pruebas de integración
                }
            }
        }

        stage('Deploy to Azure Web App') {
            when {
                expression { currentBuild.resultIsBetterOrEqualTo('SUCCESS') }
            }
            steps {
                script {
                    // Configuración de las credenciales FTPS
                    def ftpsUsername = 'DigitAlholics1\\brancovillegasperalta'
                    def ftpsPassword = '@Admin123'
                    def ftpsHost = 'waws-prod-blu-517.ftp.azurewebsites.windows.net'
                    def ftpsPort = '21' // Puerto FTPS (generalmente 21)

                    // Ruta local del archivo JAR a desplegar
                    def localJarFilePath = './target/Backend-Theraphy-1.0.jar'

                    // Comando FTP para cargar el archivo JAR
                    bat """curl --ftp-ssl -u "${ftpsUsername}:${ftpsPassword}" --ssl-reqd -T "${localJarFilePath}" "ftps://${ftpsHost}/site/wwwroot/Backend-Theraphy-1.0.jar" """
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'DockerHubCredentials', usernameVariable: 'DOCKER_HUB_USERNAME', passwordVariable: 'DOCKER_HUB_PASSWORD')]) {
                    bat "echo \${DOCKER_HUB_PASSWORD} | docker login -u \${DOCKER_HUB_USERNAME} --password-stdin"
                }
            }
        }
        stage('Check Docker Hub Connectivity') {
            steps {
                bat 'docker login'
            }
        }
        stage('Publish Docker Image') {
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: 'DockerHubCredentials', usernameVariable: 'DOCKER_HUB_USERNAME', passwordVariable: 'DOCKER_HUB_PASSWORD')]) {
                        // Autenticación en Docker Hub
                        docker.withRegistry('https://index.docker.io/v1/', 'DOCKER_HUB_USERNAME', 'DOCKER_HUB_PASSWORD') {
                            // Publicar la imagen en Docker Hub
                            docker.image("${DOCKER_REGISTRY}/${KUBE_DEPLOYMENT}:${BUILD_NUMBER}").push()
                        }
                    }
                }
            }
        }
        stage('Deploy to Kubernetes') {
            steps {
                script {
                    // Autenticarse en el clúster de Kubernetes
                    withKubeConfig([credentialsId: 'your-kube-credentials', serverUrl: KUBE_SERVER]) {
                        // Aplicar la configuración en Kubernetes
                        bat "kubectl apply -f your-kube-config.yaml --namespace=${KUBE_NAMESPACE}"
                    }
                }
            }
        }

    }
}
