package dev.sabri.k8s.domain;

import java.util.List;
import org.springframework.data.domain.Page;

public record BookmarksDto(
    List<BookmarkDto> data,
    long totalElements,
    int totalPages,
    int currentPage,
    boolean isFirst,
    boolean isLast,
    boolean hasNext,
    boolean hasPrevious) {

  public BookmarksDto(Page<BookmarkDto> bookmarkPage) {
    this(
        bookmarkPage.getContent(),
        bookmarkPage.getTotalElements(),
        bookmarkPage.getTotalPages(),
        bookmarkPage.getNumber() + 1,
        bookmarkPage.isFirst(),
        bookmarkPage.isLast(),
        bookmarkPage.hasNext(),
        bookmarkPage.hasPrevious());
  }
}
