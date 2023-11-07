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
                script {
                    // Construir la imagen de Docker
                    docker.build("${DOCKER_REGISTRY}/${KUBE_DEPLOYMENT}:${BUILD_NUMBER}")
                }
            }
        }

        stage('Publish Docker Image') {
            steps {
                script {

                    // Autenticación en Docker Hub con tu nombre de usuario y contraseña
                    bat "docker login docker.io -u mundex -p @Db2c3R4C"


                    // Publica la imagen en Docker Hub
                    docker.image("${mundex}/${KUBE_DEPLOYMENT}:${BUILD_NUMBER}").push()
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
