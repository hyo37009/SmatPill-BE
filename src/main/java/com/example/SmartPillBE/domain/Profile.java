package com.example.SmartPillBE.domain;

import jakarta.persistence.*;
import lombok.Getter;
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

    private String name;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birth;

    private float height;
    private float weight;

    @Enumerated(EnumType.STRING)
    private Gender sex;

    private String nickname;
    private boolean isRepresentative = false;

    private int age;

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

}
