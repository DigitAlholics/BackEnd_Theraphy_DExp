pipeline {
    agent any
    environment {
        DOCKER_REGISTRY = 'docker.io'  // Reemplaza con tu registro de Docker
        KUBE_NAMESPACE = 'DigitAlholics3'  // Reemplaza con el espacio de nombres de Kubernetes
        KUBE_DEPLOYMENT = 'digitalholics3-deployment'  // Reemplaza con el nombre del despliegue en Kubernetes
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
                    docker.build("mundex/${KUBE_DEPLOYMENT}:${BUILD_NUMBER}")
                }
            }
        }

        stage('Publish Docker Image') {
            steps {
                script {

                    withCredentials([string(credentialsId: 'DockerAccessToken', variable: 'dckr_pat_iIzT67jbt2PGZxJ51gC1ht33m9I')]) {
                        // Autenticación en Docker Hub con el nuevo token de acceso personal
                        bat "docker login -u mundex -p $dckr_pat_iIzT67jbt2PGZxJ51gC1ht33m9I"
                        // Publica la imagen en Docker Hub
                        docker.image("mundex/${KUBE_DEPLOYMENT}:${BUILD_NUMBER}").push()
                    }

                    // Autenticación en Docker Hub con tu nombre de usuario y contraseña
                    //bat "docker login docker.io -u mundex -p admin1234"


                    // Publica la imagen en Docker Hub
                    //docker.image("mundex/${KUBE_DEPLOYMENT}:${BUILD_NUMBER}").push()
                }
            }
        }
        stage('Replace IMAGE_TAG in YAML') {
            steps {
                script {
                    def yamlFilePath = 'AKS_webapp.yaml' // Ruta al archivo YAML
                    def imageTag = env.BUILD_NUMBER // O el valor deseado para IMAGE_TAG

                    // Leer el contenido del archivo YAML
                    def yamlContent = readFile(yamlFilePath)

                    // Reemplazar IMAGE_TAG en el contenido YAML
                    yamlContent = yamlContent.replaceAll('${IMAGE_TAG}', imageTag)

                    // Escribir el contenido actualizado de vuelta al archivo
                    writeFile(file: yamlFilePath, text: yamlContent)
                }
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                script {
                    // Iniciar sesión en Azure (asegúrate de que la CLI de Azure esté instalada en tu servidor Jenkins)
                    bat 'az login --use-device-code'

                    // Establecer la suscripción
                    bat 'az account set --subscription 18c0accd-670d-4555-9f17-23e1d4ab0603'

                    // Descargar credenciales del clúster de Kubernetes
                    bat 'az aks get-credentials --resource-group DigitAlholics3 --name DigitAlholics3'

                    // Aplicar configuraciones en Kubernetes (reemplaza esto por tus comandos)
                    bat 'kubectl apply -f AKS_webapp.yaml --namespace=default'
                }
            }
        }

    }
}
