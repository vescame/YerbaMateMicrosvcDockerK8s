# defaul shell
SHELL = /bin/sh

# Rule "help"
.PHONY: help
.SILENT: help
help:
	echo "Use make [RULES]"
	echo ""
	echo "Main RULES:"
	echo ""
	echo "run-network		- create docker network"
	echo "rm-network		- rm docker network"
	echo ""
	echo "run			- run application on docker"
	echo "rm			- stop and delete application"
	echo ""
	echo "run-db			- run checkout db on docker"
	echo "rm-db			- stop and delete databases"
	echo ""
	echo "run-broker		- run checkout db on docker"
	echo "rm-broker		- stop and delete databases"
	echo ""
	echo "check			- check tools versions"
	echo "help			- show this message"

build: build-checkout build-payment build-mailer

build-checkout: run-db-checkout
	gradle -p yerbamate.checkout build
	docker build --force-rm -t yerbamate-checkout --no-cache \
		-f yerbamate.checkout/Dockerfile \
		yerbamate.checkout/

build-payment: run-db-payment
	gradle -p yerbamate.payment build
	docker build --force-rm -t yerbamate-payment --no-cache \
		-f yerbamate.payment/Dockerfile \
		yerbamate.payment/

build-mailer: run-db-mailer
	gradle -p yerbamate.mailer build
	docker build --force-rm -t yerbamate-mailer --no-cache \
		-f yerbamate.mailer/Dockerfile \
		yerbamate.mailer/

run-network:
	docker network create yerbamatedev

rm-network:
	docker network remove yerbamatedev

# RUN
run: run-network run-broker run-checkout run-payment run-mailer

run-checkout: build-checkout
	docker run -d --name yerbamate-checkout --hostname checkout \
		-p 8085:8085 \
		--network yerbamatedev \
		yerbamate-checkout

run-payment: build-payment
	docker run -d --name yerbamate-payment --hostname payment \
		-p 8086:8086 \
		--network yerbamatedev \
		yerbamate-payment

run-mailer: build-mailer
	docker run -d --name yerbamate-mailer --hostname mailer \
		-p 8087:8087 \
		--network yerbamatedev \
		yerbamate-mailer

# STOP
stop: stop-checkout stop-payment stop-mailer

stop-checkout:
	docker stop yerbamate-checkout

stop-payment:
	docker stop yerbamate-payment

stop-mailer:
	docker stop yerbamate-mailer

# REMOVE
rm: stop rm-checkout rm-payment rm-mailer

rm-checkout: stop-checkout
	docker rm yerbamate-checkout

rm-payment: stop-payment
	docker rm yerbamate-payment

rm-mailer: stop-mailer
	docker rm yerbamate-mailer

# RUN DB
run-db: run-db-checkout run-db-payment run-db-mailer

run-db-checkout: stop-db-checkout rm-db-checkout
	docker run -d --name db-checkout --hostname dbcheckout \
		-p 5432:5432  \
		-e POSTGRES_USER=checkout_db_user \
		-e POSTGRES_PASSWORD=checkout_db_password \
		-e POSTGRES_DB=checkout \
		--network yerbamatedev \
		postgres:latest

run-db-payment: stop-db-payment rm-db-payment
	docker run -d --name db-payment --hostname dbpayment \
		-p 5433:5432 \
		-e POSTGRES_DB=payment \
		-e POSTGRES_USER=payment_db_user \
		-e POSTGRES_PASSWORD=payment_db_password \
		--network yerbamatedev \
		postgres:latest

run-db-mailer: stop-db-mailer rm-db-mailer
	docker run -d --name db-mailer --hostname dbmailer \
		-p 5434:5432 \
		-e POSTGRES_DB=mailer \
		-e POSTGRES_USER=mailer_db_user \
		-e POSTGRES_PASSWORD=mailer_db_password \
		--network yerbamatedev \
		postgres:latest

# STOP DB
stop-db: stop-db-checkout stop-db-payment stop-db-mailer

stop-db-checkout:
	- docker stop db-checkout

stop-db-payment:
	- docker stop db-payment

stop-db-mailer:
	- docker stop db-mailer

# REMOVE DB
rm-db: stop-db rm-db-checkout rm-db-payment rm-db-mailer

rm-db-checkout: stop-db-checkout
	- docker rm db-checkout

rm-db-payment: stop-db-payment
	- docker rm db-payment

rm-db-mailer: stop-db-mailer
	- docker rm db-mailer

# KAFKA
run-broker: run-zookeeper run-kafka run-schema-registry

rm-broker: rm-zookeeper rm-kafka rm-schema-registry

run-kafka:
	docker run -d --name kafka --hostname broker \
		-p 29092:29092 \
		-p 9092:9092 \
		-e KAFKA_BROKER_ID=1 \
		-e KAFKA_ZOOKEEPER_CONNECT=zookeeper:2181 \
		-e KAFKA_LISTENER_SECURITY_PROTOCOL_MAP='PLAINTEXT:PLAINTEXT,PLAINTEXT_HOST:PLAINTEXT' \
		-e KAFKA_ADVERTISED_LISTENERS='PLAINTEXT://broker:29092,PLAINTEXT_HOST://broker:9092' \
		-e KAFKA_METRIC_REPORTERS='io.confluent.metrics.reporter.ConfluentMetricsReporter' \
		-e KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR=1 \
		-e KAFKA_GROUP_INITIAL_REBALANCE_DELAY_MS=0 \
		-e CONFLUENT_METRICS_REPORTER_BOOTSTRAP_SERVERS=broker:9092 \
		-e CONFLUENT_METRICS_REPORTER_ZOOKEEPER_CONNECT=zookeeper:2181 \
		-e CONFLUENT_METRICS_REPORTER_TOPIC_REPLICAS=1 \
		-e CONFLUENT_METRICS_ENABLE='true' \
		-e CONFLUENT_SUPPORT_CUSTOMER_ID=anonymous \
		--network yerbamatedev \
		confluentinc/cp-enterprise-kafka:latest

run-zookeeper:
	docker run -d --name zookeeper --hostname zookeeper \
		-p 2181:2181 \
		-e ZOOKEEPER_CLIENT_PORT=2181 \
		-e ZOOKEEPER_TICK_TIME=2000 \
		--network yerbamatedev \
		confluentinc/cp-zookeeper:latest

run-schema-registry:
	docker run -d --name schema-registry --hostname schemaregistry \
		-p 8081:8081 \
		-e SCHEMA_REGISTRY_HOST_NAME=schema-registry \
		-e SCHEMA_REGISTRY_KAFKASTORE_CONNECTION_URL=zookeeper:2181 \
		--network yerbamatedev \
		confluentinc/cp-schema-registry:latest

stop-zookeeper:
	docker stop zookeeper

rm-zookeeper: stop-zookeeper
	docker rm zookeeper

stop-kafka:
	docker stop kafka

rm-kafka: stop-kafka
	docker rm kafka

stop-schema-registry:
	docker stop schema-registry

rm-schema-registry: stop-schema-registry
	docker rm schema-registry

