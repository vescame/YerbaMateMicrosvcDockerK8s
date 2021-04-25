# build with gradle
FROM gradle:jre14 as gradle-builder
COPY ./ /tmp/ws
WORKDIR /tmp/ws
RUN payment_db_host=dbpayment gradle build

# final stage, openjdk
FROM openjdk:14-alpine
RUN mkdir /usr/yerbamate.payment
WORKDIR /usr/yerbamate.payment
COPY --from=gradle-builder \
    /tmp/ws/build/libs/yerbamate.payment-0.0.1-SNAPSHOT.jar .
EXPOSE 8086
ENTRYPOINT [ "sh", "-c", "java --enable-preview $JAVA_OPTS -jar yerbamate.payment-0.0.1-SNAPSHOT.jar" ]
