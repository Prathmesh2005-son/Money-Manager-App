pipeline {
    agent any

    stages {
        stage('Clone') {
            steps {
                git 'https://github.com/Prathmesh2005-son/Money-Manager-App.git'
            }
        }

        stage('Build APK') {
            steps {
                sh 'chmod +x gradlew'
                sh './gradlew assembleDebug'
            }
        }

        stage('Archive APK') {
            steps {
                archiveArtifacts artifacts: 'app/build/outputs/**/*.apk'
            }
        }
    }
}