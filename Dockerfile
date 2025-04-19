


FROM openjdk:17-jdk-alpine

# set the working directory in the container
WORKDIR /app

# Copy the local server jar to the container
COPY target/springboot-mall-0.0.1-SNAPSHOT.jar app.jar

#Expose port 8000 for the server
EXPOSE 8000


ENTRYPOINT ["java", "-jar" , "app.jar"]