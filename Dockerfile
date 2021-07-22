#FROM openjdk:8-jdk-alpine
#RUN addgroup -S spring && adduser -S spring -G spring
#USER spring:spring
#ARG DEPENDENCY=target/dependency
#COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
#COPY ${DEPENDENCY}/META-INF /app/META-INF
#COPY ${DEPENDENCY}/BOOT-INF/classes /app
#ENTRYPOINT ["java","-cp","app:app/lib/*","hello.Application"]

FROM adoptopenjdk/openjdk11:alpine
RUN apk add ffmpeg
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
#RUN ffmpeg
#RUN ffprobe
RUN mkdir -p resources/data
COPY src/main/resources/data/* resources/data/
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=docker","/app.jar"]
