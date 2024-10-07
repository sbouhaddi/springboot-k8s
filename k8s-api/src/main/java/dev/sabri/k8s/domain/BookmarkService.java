package dev.sabri.k8s.domain;

import dev.sabri.k8s.exceptions.ResourceNotFoundException;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class BookmarkService {
  private final BookmarkRepository bookmarkRepository;
  private static final BookmarkMapper bookmarkMapper = BookmarkMapper.INSTANCE;

  @Transactional(readOnly = true)
  public BookmarksDto getAllBookmarks(final Integer page) {
    val pageNo = page < 1 ? 0 : page - 1;
    val pageable = PageRequest.of(pageNo, 10, Sort.Direction.DESC, "createdAt");
    return new BookmarksDto(
        bookmarkRepository.findAll(pageable).map(bookmarkMapper::bookmarkToBookmarkDTO));
    /* return new BookmarksDto(
    bookmarkRepository.findBookmarks(pageable));*/
  }

  @Transactional(readOnly = true)
  public BookmarksDto searchBookmarks(final String query, final Integer page) {
    val pageNo = page < 1 ? 0 : page - 1;
    val pageable = PageRequest.of(pageNo, 10, Sort.Direction.DESC, "createdAt");
    // val bookmarks = bookmarkRepository.searchBookmarks(query, pageable);
    val bookmarks =
        bookmarkRepository
            .findByDescriptionContainsIgnoreCase(query, pageable)
            .map(bookmarkMapper::bookmarkToBookmarkDTO);
    return new BookmarksDto(bookmarks);
  }

  public BookmarkDto createBookmark(final CreateBookmarkRequest createBookmarkRequest) {
    val bookmark =
        bookmarkRepository.save(
            new Bookmark(
                null,
                createBookmarkRequest.getUrl(),
                createBookmarkRequest.getDescription(),
                Instant.now()));
    return bookmarkMapper.bookmarkToBookmarkDTO(bookmark);
  }

  public void deleteBookmark(final long id) {
    val bookmark =
        bookmarkRepository
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Bookmark not found !"));
    bookmarkRepository.delete(bookmark);
  }

  public BookmarkDto updateBookmark(final long id, final CreateBookmarkRequest updateRequest) {
    val bookmark =
        bookmarkRepository
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Bookmark not found"));
    bookmark.setDescription(updateRequest.getDescription());
    bookmark.setUrl(updateRequest.getUrl());

    val updatedBookmark = bookmarkRepository.save(bookmark);
    return bookmarkMapper.bookmarkToBookmarkDTO(updatedBookmark);
  }
}
