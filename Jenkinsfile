pipeline {
    agent any
    environment {
        TAG = '1.0'
    }
    stages {
        stage('maven clean') {
            steps {
                echo 'maven clean'
                sh 'mvn  clean'
            }
        }
        stage('build project') {
            steps {
                echo "build project"
                sh 'mvn -Dmaven.test.skip=true   package'
            }
        }
/*       stage('Dokcer build image') {
            steps {
                script {
                    echo "Docker build image"
                    dockerImage = docker.build("${REGISTRY}:${TAG}")
                    //  sh 'docker build -t tpachatproject -f Dockerfile .'
                }
            }
        }
        stage('start container') {
            steps {
                echo 'start container'
                sh 'docker-compose up -d'
                sh 'docker-compose ps'
            }
        }
       stage('maven test') {
            steps {
                echo 'unit test'
                sh 'docker exec -i ${SPRING_CONTAINER} mvn test'
            }
        }*/
        stage('SonarQube analysis') {
            steps{
                withSonarQubeEnv('sonarqube') {
                    sh 'mvn sonar:sonar'
                } // submitted SonarQube taskId is automatically attached to the pipeline context
            }

        }


//        stage('Docker hub push') {
//            steps {
//                script {
//                    echo "Docker push"
//                    withCredentials([string(credentialsId: 'dockerhub-pwd', variable: 'dockerhubpwd')]) {
//                        sh 'docker login -u chjasser -p ${dockerhubpwd}'
//                        sh 'docker push ${REGISTRY}:${TAG}'
//                        sh 'docker logout'
//                    }
//                }
//            }
//        }

//        stage('Docker pull') {
//            steps {
//                script {
//                    echo "Docker pull"
//                    withCredentials([string(credentialsId: 'dockerhub-pwd', variable: 'dockerhubpwd')]) {
//                        sh 'docker login -u chjasser -p ${dockerhubpwd}'
//                        sh 'docker pull ${REGISTRY}:${TAG}'
//                        sh 'docker logout'
//                    }
//
//                }
//            }
//        }

    //        stage('run spring boot') {
//            steps {
//                echo "run spring boot"
//                sh 'mvn spring-boot:run'
//            }
//        }
//        stage('build project') {
//            steps {
//                echo "build project"
//                sh 'mvn  package'
//            }
//        }

//        stage('stop containers') {
//            steps {
//                sh 'docker-compose down'
//                sh 'docker-compose ps -a'
//            }
//
//        }

//        stage('Dokcer Cleaning up') {
//            steps {
//                sh "docker rmi ${REGISTRY}:${TAG}"
//            }
//        }
//
//                    stage('install') {
//                        steps {
//                            echo 'install'
//                            sh 'mvn  -Dmaven.test.skip=true  clean install '
//
//                        }
//
//                    }


}
//        post {
//            failure {
//                emailext to: "cjasser40@gmail.com",
//                        subject: "jenkins build:${currentBuild.currentResult}: ${env.JOB_NAME}",
//                        body: "${currentBuild.currentResult}: Job ${env.JOB_NAME}\nMore Info can be found here: ${env.BUILD_URL}",
//                        attachLog: true,
//                        compressLog: true,
//                        recipientProviders: [buildUser(), developers(), brokenBuildSuspects()]
//            }
//
//
//        }
}