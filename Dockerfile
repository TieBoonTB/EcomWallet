FROM java:8
LABEL maintainer="ngtieboon@gmail.com"
EXPOSE 8080
WORKDIR /app
COPY target/Ecomwallet-0.0.1-SNAPSHOT.jar /app/Ecomwallet.jar
ENTRYPOINT ["java","-jar","Ecomwallet.jar"]
