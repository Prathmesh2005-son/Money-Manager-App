pipeline {
    agent any

    tools {
        jdk 'Java17'
    }

    stages {
        stage('Build APK') {
            steps {
                bat 'gradlew.bat assembleDebug --stacktrace'
            }
        }
    }
}