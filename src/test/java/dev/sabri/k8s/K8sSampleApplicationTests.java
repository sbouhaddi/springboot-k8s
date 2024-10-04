package dev.sabri.k8s;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class K8sSampleApplicationTests {

  @Test
  void contextLoads() {}
}
