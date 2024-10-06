package dev.sabri.k8s.api;

import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.sabri.k8s.domain.*;
import java.time.Instant;
import java.util.List;
import lombok.val;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestPropertySource(
    properties = {"spring.datasource.url=jdbc:tc:postgresql:14-alpine:///bookmarks"})
class BookmarkControllerTest {

  @Autowired private MockMvc mockMvc;
  @Autowired private BookmarkRepository bookmarkRepository;
  @Autowired private ObjectMapper mapper;

  @BeforeEach
  void setUp() {
    bookmarkRepository.deleteAllInBatch();
    val bookmarks =
        List.of(
            new Bookmark(null, "https://example.com", "Example Description", Instant.now()),
            new Bookmark(
                null, "https://example.org", "Another Example", Instant.now().minusSeconds(3600)),
            new Bookmark(
                null,
                "https://example.net",
                "Yet Another Example",
                Instant.now().minusSeconds(7200)),
            new Bookmark(
                null,
                "https://example1.com",
                "Example 1 Description",
                Instant.now().minusSeconds(10800)),
            new Bookmark(
                null,
                "https://example2.org",
                "Example 2 Description",
                Instant.now().minusSeconds(14400)),
            new Bookmark(
                null,
                "https://example3.net",
                "Example 3 Description",
                Instant.now().minusSeconds(18000)),
            new Bookmark(
                null,
                "https://example4.com",
                "Example 4 Description",
                Instant.now().minusSeconds(21600)),
            new Bookmark(
                null,
                "https://example5.org",
                "Example 5 Description",
                Instant.now().minusSeconds(25200)),
            new Bookmark(
                null,
                "https://example6.net",
                "Example 6 Description",
                Instant.now().minusSeconds(28800)),
            new Bookmark(
                null,
                "https://example7.com",
                "Example 7 Description",
                Instant.now().minusSeconds(32400)),
            new Bookmark(
                null,
                "https://example8.org",
                "Example 8 Description",
                Instant.now().minusSeconds(36000)),
            new Bookmark(
                null,
                "https://example9.net",
                "Example 9 Description",
                Instant.now().minusSeconds(39600)),
            new Bookmark(
                null,
                "https://example10.com",
                "Example 10 Description",
                Instant.now().minusSeconds(43200)),
            new Bookmark(
                null,
                "https://example11.org",
                "Example 11 Description",
                Instant.now().minusSeconds(46800)));
    bookmarkRepository.saveAll(bookmarks);
  }

  @AfterEach
  void tearDown() {
    bookmarkRepository.deleteAllInBatch();
  }

  @Test
  void should_GetBookmark() throws Exception {
    // Act & Assert
    mockMvc
        .perform(get("/api/bookmarks"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.totalElements").value(14));
  }

  @ParameterizedTest
  @CsvSource({"1,14,2,1,true,false,true,false", "2,14,2,2,false,true,false,true"})
  void getBookmarks_ValidPage_ReturnsBookmarks(
      final int pageNumber,
      final long totalElements,
      final int totalPages,
      final int currentPage,
      final boolean isFirst,
      final boolean isLast,
      final boolean hasNext,
      final boolean hasPrevious)
      throws Exception {
    // Act & Assert
    mockMvc
        .perform(get("/api/bookmarks").param("page", String.valueOf(pageNumber)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.bookmarks").isArray())
        .andExpect(jsonPath("$.totalElements").value(totalElements))
        .andExpect(jsonPath("$.totalPages").value(totalPages))
        .andExpect(jsonPath("$.currentPage").value(currentPage))
        .andExpect(jsonPath("$.isFirst").value(isFirst))
        .andExpect(jsonPath("$.isLast").value(isLast))
        .andExpect(jsonPath("$.hasNext").value(hasNext))
        .andExpect(jsonPath("$.hasPrevious").value(hasPrevious));
  }

  @Test
  void getBookmarks_DefaultPage_ReturnsBookmarks() throws Exception {
    // Act & Assert
    mockMvc
        .perform(get("/api/bookmarks"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.bookmarks").isArray())
        .andExpect(jsonPath("$.totalElements").value(14))
        .andExpect(jsonPath("$.totalPages").value(2))
        .andExpect(jsonPath("$.currentPage").value(1))
        .andExpect(jsonPath("$.isFirst").value(true))
        .andExpect(jsonPath("$.isLast").value(false))
        .andExpect(jsonPath("$.hasNext").value(true))
        .andExpect(jsonPath("$.hasPrevious").value(false));
  }

  @Test
  void createBookmark_ValidRequest_ReturnsCreatedBookmark() throws Exception {
    // Arrange
    val createBookmarkRequest = new CreateBookmarkRequest();
    createBookmarkRequest.setDescription("Test Description 99");
    createBookmarkRequest.setUrl("https://example99.com");

    // Act & Assert
    mockMvc
        .perform(
            post("/api/bookmarks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writer().writeValueAsString(createBookmarkRequest)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id", notNullValue()))
        .andExpect(jsonPath("$.url").value("https://example99.com"))
        .andExpect(jsonPath("$.description").value("Test Description 99"));
  }

  @Test
  void createBookmark_InvalidRequest_ReturnsBadRequest() throws Exception {
    // Arrange
    CreateBookmarkRequest createBookmarkRequest = new CreateBookmarkRequest();
    createBookmarkRequest.setUrl("https://example.com"); // Invalid as description is empty

    // Act & Assert
    mockMvc
        .perform(
            post("/api/bookmarks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writer().writeValueAsString(createBookmarkRequest)))
        .andExpect(status().isBadRequest());
  }
}
