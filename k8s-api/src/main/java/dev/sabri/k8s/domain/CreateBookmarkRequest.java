package dev.sabri.k8s.domain;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateBookmarkRequest {
  @NotEmpty(message = "Description should not be empty !")
  private String description;

  @NotEmpty(message = "Url should not be empty !")
  private String url;
}
