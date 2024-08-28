FROM eclipse-temurin:21-jre-jammy
ENV TZ="Europe/Moscow"
VOLUME /tmp
COPY target/* .
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
EXPOSE 9090
ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar /app.jar --server.port=9090 --spring.jackson.serialization.fail-on-empty-beans=false --spring.sql.init.mode=always"]