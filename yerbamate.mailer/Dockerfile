FROM openjdk:15-alpine

RUN mkdir /usr/yerbamate.mailer

COPY build/libs/yerbamate.mailer-0.0.1-SNAPSHOT.jar /usr/yerbamate.mailer
WORKDIR /usr/yerbamate.mailer

EXPOSE 8087

ENTRYPOINT [ "sh", "-c", "java --enable-preview $JAVA_OPTS -jar yerbamate.mailer-0.0.1-SNAPSHOT.jar" ]
