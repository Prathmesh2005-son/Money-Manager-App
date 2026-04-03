pipeline {
    agent any

    stages {
        stage('Clone') {
            steps {
                git branch: 'main', url: 'https://github.com/Prathmesh2005-son/Money-Manager-App.git'
            }
        }

        stage('Build APK') {
            steps {
                echo 'Building APK...'
            }
        }

        stage('Archive APK') {
            steps {
                echo 'Archiving APK...'
            }
        }
    }
}