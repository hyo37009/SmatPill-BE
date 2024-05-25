package com.example.SmartPillBE.service;

import com.example.SmartPillBE.domain.Bookmark;
import com.example.SmartPillBE.domain.Pill;
import com.example.SmartPillBE.domain.Profile;
import com.example.SmartPillBE.repository.BookmarkRepository;
import com.example.SmartPillBE.repository.PillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookmarkService {

    private final ProfileService profileService;
    private final PillService pillService;
    private final BookmarkRepository bookmarkRepository;

    @Transactional
    public Long CreateBookmark(String pillNumber, int profileId) throws Exception {
        Profile profile = profileService.getProfile(profileId);
        Pill pill = pillService.findByNumber(pillNumber);
        Bookmark bookmark = Bookmark.createBookmark(profile, pill);
        bookmarkRepository.save(bookmark);
        return bookmark.getId();
    }

    public Bookmark findByProfileIdAndPillNumber(int profileId, String pillNumber) throws Exception {
        Profile profile = profileService.getProfile(profileId);
        Pill pill = pillService.findByNumber(pillNumber);

        Bookmark bookmark = bookmarkRepository.findByProfileAndPillNumber(profile, pill);
        if(bookmark == null){
            throw new Exception("해당 약의 북마크가 존재하지 않습니다.");
        }
        return bookmark;
    }

    @Transactional
    public void setMemo(Long id, String memo){
        Bookmark bookmark = bookmarkRepository.findOne(id);
        bookmark.setMemo(memo);
    }

    public String getMemo(int profileId, String pillNumber) throws Exception {
        Profile profile = profileService.getProfile(profileId);
        Pill pill = pillService.findByNumber(pillNumber);
        Bookmark bookmark = bookmarkRepository.findByProfileAndPillNumber(profile, pill);
        return bookmark.getMemo();
    }

    public List<Bookmark> getBookmarks(int profileId) throws Exception {
        Profile profile = profileService.getProfile(profileId);
        return bookmarkRepository.findByProfile(profile);
    }

    public void deleteBookmark(int profileId, String pillNumber) throws Exception {
        Profile profile = profileService.getProfile(profileId);
        Pill pill = pillService.findByNumber(pillNumber);
        Bookmark bookmark = bookmarkRepository.findByProfileAndPillNumber(profile, pill);
        if(bookmark == null){
            throw new Exception("존재하지 않는 북마크입니다.");
        }
        bookmarkRepository.delete(bookmark);

    }
}
