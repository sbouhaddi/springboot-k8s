package dev.sabri.k8s.domain;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BookmarkMapper {
  BookmarkMapper INSTANCE = Mappers.getMapper(BookmarkMapper.class);

  BookmarkDto bookmarkToBookmarkDTO(Bookmark bookmark);

  Bookmark bookmarkDTOToBookmark(BookmarkDto bookmarkDTO);
}
