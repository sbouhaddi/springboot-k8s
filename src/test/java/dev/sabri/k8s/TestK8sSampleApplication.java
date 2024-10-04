package dev.sabri.k8s;

import org.springframework.boot.SpringApplication;

public class TestK8sSampleApplication {

  public static void main(String[] args) {
    SpringApplication.from(K8sSampleApplication::main)
        .with(TestcontainersConfiguration.class)
        .run(args);
  }
}
