FROM openjdk:15-alpine

RUN mkdir /usr/yerbamate.payment

COPY build/libs/yerbamate.payment-0.0.1-SNAPSHOT.jar /usr/yerbamate.payment
WORKDIR /usr/yerbamate.payment

EXPOSE 8086

ENTRYPOINT [ "sh", "-c", "java --enable-preview $JAVA_OPTS -jar yerbamate.payment-0.0.1-SNAPSHOT.jar" ]
