package dev.sabri.k8s.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
  @Query(
      "SELECT new dev.sabri.k8s.domain.BookmarkDto(b.id, b.url, b.description, b.createdAt ) FROM Bookmark b")
  Page<BookmarkDto> findBookmarks(Pageable pageable);
}
