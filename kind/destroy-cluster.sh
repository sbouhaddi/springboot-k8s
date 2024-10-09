#!/bin/sh

printf "Destroying Kubernetes cluster..."

kind delete cluster --name sb-k8s-sample