# springboot-k8s

# How to build a docker image
mvn jib:build

mvn jib:dockerBuild

mvn spring-boot:build-image

docker run  -p 8080:8080 sbouhaddi/k8s-api
