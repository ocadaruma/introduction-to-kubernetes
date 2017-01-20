# introduction-to-kubernetes
Introduction to kubernetes.

## Prerequisites

- minikube : v0.14.0
- sbt : 0.13.x

## 0. Clone repository

```bash
$ git clone https://github.com/ocadaruma/introduction-to-kubernetes
```

## 1. Build image

```bash
$ eval $(minikube docker-env)
$ cd app
$ sbt docker
$ docker images
REPOSITORY                                            TAG                 IMAGE ID            CREATED             SIZE
example/example                                       latest              ac5970138dba        About an hour ago   144.4 MB
example/example                                       v1.0-SNAPSHOT       4a6bdb4a8719        8 hours ago         144.4 MB
```

## 2. Create services

```bash
$ cd ../k8s
$ kubectl create -f services.yaml
service "mysql-service" created
service "example-app" created
$ kubectl get svc
NAME            CLUSTER-IP   EXTERNAL-IP   PORT(S)    AGE
example-app     10.0.0.24    <nodes>       3000/TCP   40s
kubernetes      10.0.0.1     <none>        443/TCP    54d
mysql-service                10.0.2.2      3306/TCP   40s
$ kubectl get svc example-app -o 'jsonpath={.spec.ports[0].nodePort}'
32425
```

## 3. Deploy application

```bash
$ kubectl apply -f deployment.yaml
deployment "example-app-deployment" created
$ kubectl get po                                                                                                                                                                   master
NAME                                      READY     STATUS    RESTARTS   AGE
example-app-deployment-2393591303-86kcz   1/1       Running   0          18s
example-app-deployment-2393591303-8nn4g   1/1       Running   0          18s
example-app-deployment-2393591303-ldbx1   1/1       Running   0          18s
```

![screen-shot](https://raw.githubusercontent.com/ocadaruma/introduction-to-kubernetes/master/example-app_screen-shot.png)
