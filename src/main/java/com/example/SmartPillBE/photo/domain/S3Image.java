package com.example.SmartPillBE.photo.domain;

import com.example.SmartPillBE.domain.Profile;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class S3Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    private Profile profile;
//
    private String s3Url;

    private String category;

    public S3Image(Profile profile, String s3Url, String category) {
        this.profile = profile;
        this.s3Url = s3Url;
        this.category = category;
    }

    @Override
    public String toString() {
        return "FileEntity{" +
                "id=" + id +
                ", Profile_id='" + profile.getId() + '\'' +
                ", s3Url='" + s3Url + '\'' +
                '}';
    }
}
