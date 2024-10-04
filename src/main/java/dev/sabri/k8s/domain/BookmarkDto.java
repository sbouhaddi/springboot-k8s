package dev.sabri.k8s.domain;

import java.time.Instant;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookmarkDto {
  private Long id;
  private String url;
  private String description;
  private Instant createdAt;
}
