package dev.sabri.k8s.api;

import dev.sabri.k8s.domain.BookmarkDto;
import dev.sabri.k8s.domain.BookmarkService;
import dev.sabri.k8s.domain.BookmarksDto;
import dev.sabri.k8s.domain.CreateBookmarkRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public BookmarkDto createBookmark(
      @RequestBody @Valid CreateBookmarkRequest createBookmarkRequest) {
    return bookmarkService.createBookmark(createBookmarkRequest);
  }
}
