package com.example.SmartPillBE.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "bookmark")
public class Bookmark {
    @Id
    @GeneratedValue
    @Column(name = "bookmark_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    private Profile profile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pill_number")
    private Pill pill;

    private String memo;

    // 연관관계 메서드 //
    public static Bookmark createBookmark(Profile profile, Pill pill){
        Bookmark bookmark = new Bookmark();
        bookmark.profile = profile;
        bookmark.pill = pill;
        return bookmark;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
