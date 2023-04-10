FROM amazoncorretto:17-alpine-jdk
COPY target/*.jar foxcom-test-task.jar
ENTRYPOINT ["java","-jar","/foxcom-test-task.jar"]