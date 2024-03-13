FROM openjdk:21
WORKDIR /app
COPY target/ekwateur-billing-app-0.0.1-SNAPSHOT.jar .
EXPOSE 8080
ENTRYPOINT ["java","-jar","ekwateur-billing-app-0.0.1-SNAPSHOT.jar"]