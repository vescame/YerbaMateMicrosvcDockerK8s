FROM openjdk:15-alpine

RUN mkdir /usr/yerbamate.checkout

COPY build/libs/yerbamate.checkout-0.0.1-SNAPSHOT.jar /usr/yerbamate.checkout
WORKDIR /usr/yerbamate.checkout

EXPOSE 8085

ENTRYPOINT [ "sh", "-c", "java --enable-preview $JAVA_OPTS -jar yerbamate.checkout-0.0.1-SNAPSHOT.jar" ]
