#!/bin/sh

curl -X POST \
    -H "Content-Type: application/json" \
    -d "@checkout-test.json" \
    http://localhost:8085/api/v1/checkout/

