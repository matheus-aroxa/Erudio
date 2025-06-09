FROM eclipse-temurin:24-jdk
WORKDIR /app
COPY target/curso-erudio-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]