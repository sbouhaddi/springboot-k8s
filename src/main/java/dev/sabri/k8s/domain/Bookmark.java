package dev.sabri.k8s.domain;

import jakarta.persistence.*;
import java.time.Instant;
import lombok.*;

@Entity
@Table(name = "bookmarks")
@Getter
@Setter
@NoArgsConstructor
public class Bookmark {

  @Id
  @SequenceGenerator(name = "bm_id_seq_gen", sequenceName = "bm_id_seq")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bm_id_seq_gen")
  private Long id;

  @Column(nullable = false)
  private String url;

  @Column(nullable = false)
  private String description;

  private Instant createdAt;
}
