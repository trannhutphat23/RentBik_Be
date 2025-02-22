FROM eclipse-temurin:21
MAINTAINER trannhutphattv@gmail.com
WORKDIR /src

COPY target/RentBik-0.0.1-SNAPSHOT.jar RentBik-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "RentBik-0.0.1-SNAPSHOT.jar"]