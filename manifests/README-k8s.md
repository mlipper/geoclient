# Deploy Geoclient to Kubernetes

## HOWTO - Minikube

### Asumptions

This section assumes:

* Working installation of [Minikube](https://minikube.sigs.k8s.io/docs/start/).
* Minikube is using the default, out-of-the-box support for `PersistentVolume` of type [`hostPath`](https://minikube.sigs.k8s.io/docs/handbook/persistent_volumes/).
* Minikube can connect to the Internet to download images (among other things) from the public registries like [Docker Hub](https://hub.docker.com/).
* Working installation of `kubectl`. This should be a recent version of `kubectl` that has built-in support for [`kustomize`](https://kustomize.io/).
  * Note that Minikube includes `kubectl` and it is run using `minikube kubectl ...`. This HOWTO assumes you've either got `kubectl` installed separately or are using Minikube's built-in but have aliased `minikube kubectl` to `kubectl`.
* The Geoclient version you are deploying has been built as an image using a tool like Docker and pushed to Docker Hub.
  * For examples of Geoclient `Dockerfiles` in this project, see the `images` and `ci` directories.
  * Minikube supports using a local container registry (e.g., Docker Desktop) to avoid having push images to remote registries. See Minikube's documentation on [configuring registries](https://minikube.sigs.k8s.io/docs/handbook/registry/) and [pushing images](https://minikube.sigs.k8s.io/docs/handbook/pushing/).

### Start Minikube

```sh
minikube start
```

### Configure `kubectl`

Configure `kubectl` to use Minikube. The following assumes you're using the default context name `minikube`:

```sh
kubectl config use-context minikube
```

### Use `kubectl` and `kustomize` to deploy to Minikube

This tutorial uses [`kustomize`](https://kustomize.io/) to provide a simple templating mechanism which simplifies configuring Geoclient and Geosupport versions and environment variables in a single `kustomization.yaml`. To run `kustomize` commands with `kubectl`, add the `-k` switch.

From your shell, `cd` into the `<project root>/manifests` directory.

If this is the first time you're deploying Geoclient to this Minikube cluster, some one-time setup is required:

```sh
# Create the  gis-apps Namespace
kubectl create -f namespace.yaml

# Create the PersistentVolumeClaim used to mount a Geosupport installation into the Pods' filesystem.
kubectl apply -f pvc-geosupport.yaml
```

Now use `kustomize` to template the manifest files (`deployment.yaml`, `hpa.yaml`, `service.yaml`) and apply them with `kubectl`:

```sh
kubectl apply -k .
```

This will create the `geoclient-v2` Service, Deployment, andf HorizontalPodAutoscaling objects. This take a minute or two (depending on your hardware, network connection, Minikube configuration, etc.). The next step requires that all pods be up and running. To see the progress of pod initializations, add the watch switch, `-w`, to the `get pods` command:

```sh
kubectl get pods -w

NAME                            READY   STATUS     RESTARTS   AGE
geoclient-v2-75899fdc94-drtnn   0/1     Init:0/1   0          16s
geoclient-v2-75899fdc94-p2v5l   0/1     Running    0          31s
```

In the example above, you can see that one pod is up and running but the other is still initializing.

```sh
NAME                            READY   STATUS    RESTARTS      AGE
geoclient-v2-75899fdc94-drtnn   1/1     Running   0             58m
geoclient-v2-75899fdc94-p2v5l   1/1     Running   1 (58m ago)   59m
```

Once both are running, you need to make the `geoclient-v2` service visible to your host machine. To do this, use the `kubectl port-forward` command. The syntax for this command is `kubectl port-forward service/<service name> <host port>:<service port>`.

Run:

```sh
kubectl port-forward service/geoclient-v2 8081:8080
```

Note that the `kubectl port-forward ...` command above will block your terminal (unless you run it in the background by appending a `&` to the end of the line).

## Bask in the glory of your handiwork

Now, geoclient-v2 is available on `<host port>` (your workstation) `8081`. The base endpoint URL will be `http://localhost:8081/geoclient/v2`. However, to do anything useful you need to call a geoclient function (or "operation"). Try, for example:

The [`/version` endpoint](http://localhost:8081/geoclient/v2/version) in your browser.

Or with curl:

```sh
curl -s --get http://localhost:8081/geoclient/v2/version
```

Note that if you're making Geoclient requests in a browser, the service is exposed with `http` and not `https`. Many browsers automatically convert `http` to `https` if you're cutting and pasting or double-clicking a link.
