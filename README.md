# springboot-k8s

# How to start app
```shell
git clone https://github.com/sbouhaddi/springboot-k8s.git
cd springboot-k8s
./run.sh start
./run.sh stop


./run.sh start_infra
./run.sh stop_infra
```

* How to start database dependencies

```shell
./run.sh start_infra
./run.sh stop_infra
```



# How to build a docker image with different méthods
* google jib
```shell
mvn jib:build
mvn jib:dockerBuild
```
* spring boot plugin
```shell
mvn spring-boot:build-image
```
* run docker image locally
```shell
docker run  -p 8080:8080 sbouhaddi/k8s-api
```

## Docker compose Useful tips
```shell
docker-compose up -d
docker-compose logs -f
docker compose -f docker-compose.yml -f docker-compose-app.yml up  -d
```
## Access pod (Windows)
```shell
winpty kubectl exec -i -t -n default k8s-api -c k8s-api -- sh -c "clear; (bash || ash || sh)"
```