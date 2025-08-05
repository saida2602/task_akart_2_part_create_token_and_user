FROM openjdk:17
COPY build/libs/akart_task-0.0.1-SNAPSHOT.jar /app.jar
CMD ["java","-jar","/app.jar"]