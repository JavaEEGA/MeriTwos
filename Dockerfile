FROM eclipse-temurin:19-jre-jammy
COPY /target/meriTwos-*.jar /app/app.jar
WORKDIR /app
EXPOSE 8080
ENTRYPOINT ["java","-jar", "/app/app.jar"]
