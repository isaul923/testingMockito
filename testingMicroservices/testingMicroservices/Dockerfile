
FROM openjdk:20-ea-19-jdk
#FROM maven:latest

WORKDIR /app


COPY  ./target/testingMicroservices-0.0.1-SNAPSHOT.jar .
COPY ./.mvn ./.mvn
COPY ./mvnw .
COPY ./pom.xml .
COPY ./src ./src


RUN sed -i -e 's/\r$//' mvnw
RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/
#RUN ./mvnw dependency:go-offline

RUN ./mvnw clean package -DskipTests




EXPOSE 8080

ENTRYPOINT ["java" , "-jar" ,"testingMicroservices-0.0.1-SNAPSHOT.jar"]