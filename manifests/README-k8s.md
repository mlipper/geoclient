# Deploy Geoclient to Kubernetes

## HOWTO - Minikube

This section assumes:

* Working installation of [Minikube](https://minikube.sigs.k8s.io/docs/start/).
* Minikube is using the default, out-of-the-box support for `PersistentVolume` of type [`hostPath`](https://minikube.sigs.k8s.io/docs/handbook/persistent_volumes/).
* Minikube can connect to the Internet to download images (among other things) from the public registries like [Docker Hub](https://hub.docker.com/).

Configure `kubectl` to use Minikube. The following assumes you're using the default context name `minikube`:

```sh
kubectl config use-context minikube
```

From your shell, `cd` into the directory containing this file (`<project root>/manifests`).

Run the following...

```sh
kubectl create -f namespace.yaml
kubectl apply -f pv-geosupport.yaml
kubectl apply -f pvc-geosupport.yaml
kubectl apply -f deployment.yaml
kubectl apply -f hpa.yaml
```

Create a `Service` to allow access to the `geoclient-v2` deployment. There are two methods:

## Method 1

Create the `Service` yourself and use standard Kubernetes port-forwarding. The syntax is `kubectl port-forward service/<service name> <host port>:<service port>`.

```sh
kubectl apply -f service.yaml
kubectl port-forward service/geoclient-v2 8081:8080
```

Note that the `kubectl port-forward ...` command above will block your terminal (unless you run it in the background by appending a `&` to the end of the line) which can sometimes be desirable.

## Method 2

Do not create the service yourself, use the `kubectl expose deployment <deployment name> --type=NodePort --port=<host post>` command. This example sets the `<host port>` to `8081`:

```sh
kubectl expose deployment geoclient-v2 --type=NodePort --port=8081`
```

## Bask in the glory of your handiwork

Both methods above make geoclient-v2 available on `<host port>` (your workstation) `8081`. The base endpoint URL will be `http://localhost:8081/geoclient/v2`. However, to do anything useful you need to call a geoclient function (or "operation"). Try, for example:

The [`/version` endpoint](http://localhost:8081/geoclient/v2/version) in your browser.

Or with curl:

```sh
curl -s --get http://localhost:8081/geoclient/v2/version
```
