FROM maven:3.8.4-openjdk-17

WORKDIR /app

COPY . /app

RUN mvn clean package

EXPOSE 3001

CMD ["java", "-jar", "target/cashback-service-0.0.1-SNAPSHOT.jar"]
