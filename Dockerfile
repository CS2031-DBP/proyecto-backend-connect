FROM openjdk:17-jdk-slim

RUN apt-get update && apt-get install -y maven

WORKDIR /app

COPY . .

EXPOSE 8080

CMD ["mvn", "spring-boot:run"]
