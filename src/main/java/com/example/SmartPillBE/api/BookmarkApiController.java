package com.example.SmartPillBE.api;

import com.example.SmartPillBE.domain.Bookmark;
import com.example.SmartPillBE.domain.Pill;
import com.example.SmartPillBE.domain.Profile;
import com.example.SmartPillBE.service.BookmarkService;
import com.example.SmartPillBE.service.PillService;
import com.example.SmartPillBE.service.ProfileService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookmarkApiController {
    private final ProfileService profileService;
    private final BookmarkService bookmarkService;
    private final PillService pillService;

    @Transactional
    @PutMapping("/api/profiles/{id}/bookmarks/{pillNumber}")
    public BookmarkResponse createOne(@PathVariable("id") int profileId, @PathVariable("pillNumber") String pillNumber) throws Exception {
        Long id = bookmarkService.CreateBookmark(pillNumber, profileId);
        return new BookmarkResponse(id, "정상적으로 생성되었습니다.");
    }

    @Transactional
    @PostMapping("/api/profiles/{id}/bookmarks/{pillNumber}")
    public BookmarkResponse setMemo(@PathVariable("id") int profileId, @PathVariable("pillNumber") String pillNumber, @RequestBody String memo) throws Exception {
        Bookmark bookmark = bookmarkService.findByProfileIdAndPillNumber(profileId, pillNumber);
        bookmark.setMemo(memo);
        return new BookmarkResponse(bookmark.getId(), "정상적으로 설정되었습니다.");
    }

    @GetMapping("/api/profiles/{id}/bookmarks/{pillNumber}")
    public String getBookmarkMemo(@PathVariable("id") int profileId, @PathVariable("pillNumber") String pillNumber) throws Exception {
        return bookmarkService.getMemo(profileId, pillNumber);
    }

    @GetMapping("api/profiles/{id}/bookmarks")
    public List<BookmarkDto> getBookmarks(@PathVariable("id") int profileId) throws Exception {
        return bookmarkService.getBookmarks(profileId).stream()
                .map( b -> new BookmarkDto(
                        b.getPill().getPillNumber(),
                        b.getMemo()))
                .collect(Collectors.toList());
    }

    @Transactional
    @DeleteMapping("/api/profiles/{id}/bookmarks/{pillNumber}")
    public String deleteBookmark(@PathVariable("id") int profileId, @PathVariable("pillNumber") String pillNumber) throws Exception {
        bookmarkService.deleteBookmark(profileId, pillNumber);
        return "정상적으로 삭제되었습니다.";
    }

    @Data
    @AllArgsConstructor
    static class BookmarkResponse {
        Long id;
        String message;
    }

    @Data
    @AllArgsConstructor
    static class BookmarkDto {
        String pillNumber;
        String memo;
    }
}
