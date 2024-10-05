package dev.sabri.k8s.domain;

import java.time.Instant;

public interface BookmarkVM {
  Long getId();

  String getDescription();

  String getUrl();

  Instant getCreatedAt();
}
