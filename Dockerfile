FROM openjdk:17-alpine
COPY ./target/Backend-Theraphy-1.0.jar /
RUN sh -c 'touch /Backend-Theraphy-1.0.jar'
ENTRYPOINT ["java","-jar","/Backend-Theraphy-1.0.jar"]
RUN chmod +x /Backend-Theraphy-1.0.jar