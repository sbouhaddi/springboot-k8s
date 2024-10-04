package dev.sabri.k8s.domain;

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
}
