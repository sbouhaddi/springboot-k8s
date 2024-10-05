package dev.sabri.k8s.api;

import dev.sabri.k8s.domain.BookmarkService;
import dev.sabri.k8s.domain.BookmarksDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bookmarks")
@RequiredArgsConstructor
public class BookmarkController {

  private final BookmarkService bookmarkService;

  @GetMapping
  public BookmarksDto getBookmarks(
      @RequestParam(value = "page", defaultValue = "1") Integer page,
      @RequestParam(value = "query", defaultValue = "") String query) {
    if (query == null || query.trim().isEmpty()) {
      return bookmarkService.getAllBookmarks(page);
    }
    return bookmarkService.searchBookmarks(query, page);
  }
}
