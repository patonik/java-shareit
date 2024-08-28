FROM eclipse-temurin:21-jre-jammy
ENV TZ="Europe/Moscow"
VOLUME /tmp
ARG JAR_FILE=target/*
COPY ${JAR_FILE} app.jar
EXPOSE 9090
ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar /app.jar"]