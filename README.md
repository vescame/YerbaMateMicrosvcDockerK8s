# Java 14 Microservices running on Kubernetes
## Docker app

### Requirements:

**Docker and Make (Optional)**

**Java 14**
**Minikube**
**Kubectl**

Help to install tools:

## Microservices on Docker:

**See help for a more detailed build up with Docker only**
```bash
make help
```

**Straightforward build up**

**Build application and docker image**

```bash
make run
```

This will:
 - build java apps
 - create a docker network for all containers
 - create databases container
 - create kafka "broker cluster" containers
 - create java apps containers


**Check**

sh ./test-json.sh

Clean Up:

```bash
make clean
```

## App on K8s:

**Obs.:**
since there are two Makefile's, we must use the one with .k8s extension 
for kubernetes with minikube

**See help for a more detailed build up with Docker only**
```bash
make -f Makefile.k8s help
```

### Straightforward build up k8s cluster
```bash
make -f Makefile.k8s k-all
```
 start minikube, enable ingress and create namespace dev-to

### Check IP

```bash
make -f Makefile.k8s k-get-ip
```

### Minikube dashboard

```bash
make -f Makefile.k8s k-dashboard
```

## After checking that all pods are UP (it may took some time)
**Check**

```bash
sh ./test-json.sh
```

If you didn't created the /etc/hosts entry, it will test once, othewise, twice


## You may add the entry like this
```bash
echo "$(minikube -p dev ip) dev" | sudo tee -a /etc/hosts
```

Then, you can try again testing
```bash
sh ./test-json.sh
```

## Deploy/Update
```bash
make -f Makefile.k8s k-deploy
```

## Undeploy
```bash
make -f Makefile.k8s k-undeploy
```

## Stop
```bash
make -f Makefile.k8s k-stop
```

## Clean Up
```bash
make -f Makefile.k8s k-destroy
```

