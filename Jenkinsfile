pipeline {
    agent any
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
                    def ftpsUsername = 'DigitAlholics1\\$brancovillegasperalta'
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
}
