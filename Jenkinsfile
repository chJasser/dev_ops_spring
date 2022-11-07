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
        stage('maven test') {
            steps {
                echo 'unit test'
                sh 'mvn test'
            }
        }
        stage('build project') {
            steps {
                echo "build project"
                sh 'mvn -Dmaven.test.skip=true   package'
            }
        }
//      stage('build docker image') {
//            steps {
//                script {
//                    echo "Docker build image"
//                    dockerImage = docker.build("${REGISTRY}:${TAG}")
//                    //  sh 'docker build -t tpachatproject -f Dockerfile .'
//                }
//            }
//        }
        stage('build docker image') {
            steps {
                echo 'start container'
                sh 'docker-compose up -d'
                sh 'docker-compose ps'
            }
        }

        stage('SonarQube analysis') {
            steps{
                withSonarQubeEnv('sonarqube') {
                    sh 'mvn  -Dmaven.test.skip=true sonar:sonar'
                } // submitted SonarQube taskId is automatically attached to the pipeline context
            }
        }

        stage('deploy to nexus') {
            steps {
                sh 'mvn -Dmaven.test.skip=true deploy'
                // submitted SonarQube taskId is automatically attached to the pipeline context
            }
        }


        stage('Docker hub push') {
            steps {
                script {

//        withCredentials([usernamePassword(credentialsId: '51a570a8-5d55-4938-8d30-349bcf10a020', passwordVariable: '', usernameVariable: ''), usernameColonPassword(credentialsId: 'dockerHubAccount', variable: 'dockerHubAccount')]) {
//            // some block
//        }
                    echo "Docker push"
                    withCredentials([string(credentialsId: 'dockerhub-pwd', variable: 'dockerhubpwd')]) {
                        sh 'docker login -u chjasser -p ${dockerhubpwd}'
                        dockerImage.push()
//                        sh 'docker push ${REGISTRY}:${TAG}'
                        sh 'docker logout'
                    }
                }
            }
        }

        stage('Docker pull') {
            steps {
                script {
                    echo "Docker pull"
                    withCredentials([string(credentialsId: 'dockerhub-pwd', variable: 'dockerhubpwd')]) {
                        sh 'docker login -u chjasser -p ${dockerhubpwd}'
                        dockerImage.pull()
//                        sh 'docker pull ${REGISTRY}:${TAG}'
                        sh 'docker logout'
                    }

                }
            }
        }

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
        post {
            failure {
                emailext to: "cjasser40@gmail.com",
                        subject: "jenkins build:${currentBuild.currentResult}: ${env.JOB_NAME}",
                        body: "${currentBuild.currentResult}: Job ${env.JOB_NAME}\nMore Info can be found here: ${env.BUILD_URL}",
                        attachLog: true,
                        compressLog: true,
                        recipientProviders: [buildUser(), developers(), brokenBuildSuspects()]
            }


        }
}