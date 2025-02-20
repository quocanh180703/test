FROM openjdk:21-jdk-slim
LABEL author="phucth"
ENV PORT=8000
COPY target/Nhom3_TT_-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
