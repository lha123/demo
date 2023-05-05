FROM java:8
WORKDIR /usr/local/
ADD ./target/demo-0.0.1-SNAPSHOT.jar .
ENTRYPOINT ["java","-jar","demo-0.0.1-SNAPSHOT.jar"]
EXPOSE 8089