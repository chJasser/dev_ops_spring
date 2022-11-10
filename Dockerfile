FROM openjdk:11
EXPOSE 8089
ADD target/devops_cicd.jar devops_cicd.jar
ENTRYPOINT ["java","-jar","devops_cicd.jar"]