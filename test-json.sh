#!/bin/sh

ip=$(minikube -p dev ip)
host=$(minikube -p dev service -n dev yerbamate-checkout-svc --url)

echo "Testing service address"
curl -X POST \
    -H "Content-Type: application/json" \
    -d "@checkout-test.json" \
    $host/api/v1/checkout/

echo '\n'

if [ -z "$(grep "$ip\Wdev" /etc/hosts)" ]; then
  echo "Must add '$ip\tdev' entry to /etc/hosts"
  exit 0
fi
echo "Testing 'dev' ingress"
curl -X POST \
    -H "Content-Type: application/json" \
    -d "@checkout-test.json" \
    http:/dev/api/v1/checkout/

echo '\n'

