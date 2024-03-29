FROM openjdk:17 as builder
WORKDIR application
ARG JAR_FILE=./target/*.jar
COPY ${JAR_FILE} app.jar
RUN java -Djarmode=layertools -jar app.jar extract
FROM openjdk:17
WORKDIR application
COPY --from=builder application/dependencies/ ./
COPY --from=builder application/spring-boot-loader/ ./
COPY --from=builder application/snapshot-dependencies/ ./
COPY --from=builder application/application/ ./
COPY --from=hengyunabc/arthas:latest /opt/arthas /opt/arthas
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]