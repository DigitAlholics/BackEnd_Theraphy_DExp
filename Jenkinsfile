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

        stage('Deploy to Dev') {
            when {
                expression { currentBuild.resultIsBetterOrEqualTo('SUCCESS') }
                }
            steps {
            // Despliegue en un entorno de desarrollo
                script {
                    azureWebAppPublish appName: 'DigitAlholics1', resourceGroup: 'DigitAlholics1', filePath: '**/target/*.jar'
                }
            }
        }

        stage('Deploy to Staging') {
            when {
                expression { currentBuild.resultIsBetterOrEqualTo('SUCCESS') }
            }
            steps {
                // Despliegue en un entorno de staging
                script {
                    azureWebAppPublish appName: 'DigitAlholics1', resourceGroup: 'DigitAlholics1', filePath: '**/target/*.jar'
                }
            }
        }

        stage('Deploy to Production') {
             when {
                expression { currentBuild.resultIsBetterOrEqualTo('SUCCESS') }
             }
             steps {
                // Despliegue en producción
                script {
                    azureWebAppPublish appName: 'DigitAlholics1', resourceGroup: 'DigitAlholics1', filePath: '**/target/*.jar'
                }
             }
        }

    }
}
