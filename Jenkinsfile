pipeline{
    agent any
    tools {
        maven 'M2_HOME'
    }

    stages {
        stage('Getting project from Git') {
            steps{
                checkout([$class: 'GitSCM', branches: [[name: '*/Oussama']], extensions: [], userRemoteConfigs: [[credentialsId: 'Mygithub', url: 'https://github.com/chJasser/dev_ops_spring.git']]])
            }
        }
        stage('Cleaning the project') {
            steps{
                sh 'mvn -B -DskipTests clean'
            }
        }

        stage('Artifact Construction') {
            steps{
                sh 'mvn -B -DskipTests package'
            }
        }
        stage('Code Analysis with SonarQube') {
            steps{
                sh 'mvn sonar:sonar -Dsonar.projectKey=DevOps -Dsonar.host.url=http://192.168.100.4:9000 -Dsonar.login=479b5ea07cbdda7d2067ed13798c59def42cd188'
            }
        }
        /*stage('docker_run') {
            steps{
                sh '''
                    docker-compose up -d
                   '''
        }*/
    }
}