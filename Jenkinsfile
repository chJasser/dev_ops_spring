pipeline {
    agent any
    environment{
    SONAR_LOGIN= '4f1b8dd395e6e44a81bfbbce3c415c7b0a5bc34e'
    SONAR_KEY = 'devops'
    SONAR_URL = 'http://192.168.1.14:9000'
    DOCKER_LOGIN = 'tayeb99'
    DOCKER_PASSWORD = 'Tayeb@1999'
    }
    stages {
        stage('clean') {
            steps {
                sh 'mvn clean'
                echo 'cleaning'
            }
        }
        stage('build') {
            steps {
                sh 'mvn package -DskipTests'
                echo 'building'
            }
        }
        stage('test') {
            steps {
                sh 'mvn test'
                echo 'testing'
            }
        }
        stage('sonar') {
            steps {
                sh 'mvn sonar:sonar -Dsonar.host.url=$SONAR_URL -Dsonar.login=$SONAR_LOGIN -Dsonar.projectKey=$SONAR_KEY'
                echo 'sonar'
            }
        }
         stage('Publish to Nexus') {
                     steps {
                         script {
                                sh 'mvn deploy -e -DskipTests'

                     }
                 }
         stage('build docker image') {
            steps {
                script {
                    sh 'docker build -t tayeb99/devops .'
                    }
                    }
             }
           stage('Docker login') {
                                 steps {
                                     script {

                                         sh 'docker login -u $DOCKER_LOGIN -p $DOCKER_PASSWORD'
                                 }
                                 }
                           stage('Pushing Docker Image') {
                                 steps {
                                     script {

                                      sh 'docker push tayeb99/devops'
                                     }
                                 }
                           }
                           stage('Run Spring && MySQL Containers') {
                                 steps {
                                     script {
                                       sh 'docker-compose up -d'
                                     }
                                 }
                             }
}
}