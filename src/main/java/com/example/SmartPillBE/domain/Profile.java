package com.example.SmartPillBE.domain;

import com.example.SmartPillBE.photo.domain.S3Image;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

/**
 * 프로필
 */
@Entity
@Getter
@Table(name = "profile")
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "profile_id", unique = true)
    private int id;

    @Setter
    private String name;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birth;

    @Setter
    private float height;
    @Setter
    private float weight;

    @Enumerated(EnumType.STRING)
    private Gender sex;

    @Setter
    private String nickname;

    private boolean isRepresentative = false;

    private int age;

    @OneToOne
    private S3Image profileImg;

    @Override
    public String toString() {
        return "Profile{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", nickname='" + nickname + '\'' +
                ", birth=" + birth +
                ", age=" + age +
                ", sex=" + sex +
                ", height=" + height +
                ", weight=" + weight +
                ", isRepresentative=" + isRepresentative +
                '}';
    }

    public static Profile createProfile(String name, LocalDate birth,
                                        float height, float weight,
                                        Gender sex, String nickname) {
        Profile profile = new Profile();
        profile.name = name;
        profile.birth = birth;
        profile.height = height;
        profile.weight = weight;
        profile.sex = sex;
        profile.nickname = nickname;
        profile.age = LocalDate.now().getYear() - birth.getYear();

        return profile;
    }

    public void setRepresentative(){
        isRepresentative = true;
    }

    public void setProfileImg(S3Image img){
        this.profileImg = img;
    }

    public void deleteProfileImg() {
        this.profileImg = null;
    }

}
