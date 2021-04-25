# build with gradle
FROM gradle:jre14 as gradle-builder
COPY ./ /tmp/ws
WORKDIR /tmp/ws
RUN mailer_db_host=dbmailer gradle build

# final stage, openjdk
FROM openjdk:14-alpine
RUN mkdir /usr/yerbamate.mailer
WORKDIR /usr/yerbamate.mailer
COPY --from=gradle-builder \
    /tmp/ws/build/libs/yerbamate.mailer-0.0.1-SNAPSHOT.jar .
EXPOSE 8087
ENTRYPOINT [ "sh", "-c", "java --enable-preview $JAVA_OPTS -jar yerbamate.mailer-0.0.1-SNAPSHOT.jar" ]
