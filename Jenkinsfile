pipeline {
    agent any

    tools {
        jdk 'Java17'
    }

    stages {
        stage('Clone') {
            steps {
                git 'https://github.com/Prathmesh2005-son/Money-Manager-App.git'
            }
        }

        stage('Build APK') {
            steps {
                bat 'gradlew.bat assembleDebug --stacktrace'
            }
        }
    }
}