# build with gradle
FROM gradle:jre14 as gradle-builder
COPY ./ /tmp/ws
WORKDIR /tmp/ws
RUN checkout_db_host=dbcheckout gradle build

# final stage, openjdk
FROM openjdk:14-alpine
RUN mkdir /usr/yerbamate.checkout
WORKDIR /usr/yerbamate.checkout
COPY --from=gradle-builder \
    /tmp/ws/build/libs/yerbamate.checkout-0.0.1-SNAPSHOT.jar .
EXPOSE 8085
ENTRYPOINT [ "sh", "-c", "java --enable-preview $JAVA_OPTS -jar yerbamate.checkout-0.0.1-SNAPSHOT.jar" ]
