package dev.sabri.k8s;

import dev.sabri.k8s.domain.Bookmark;
import dev.sabri.k8s.domain.BookmarkRepository;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@RequiredArgsConstructor
public class K8sSampleApplication {

  private final BookmarkRepository bookmarkRepository;

  public static void main(String[] args) {
    SpringApplication.run(K8sSampleApplication.class, args);
  }

  @Bean
  CommandLineRunner commandLineRunner() {
    return args -> {
      Bookmark bookmark = new Bookmark();
      bookmark.setUrl("http://my-first-bookmark.com");
      bookmark.setDescription("My first bookmark");
      bookmark.setCreatedAt(Instant.now());
      bookmarkRepository.save(bookmark);
    };
  }
}
