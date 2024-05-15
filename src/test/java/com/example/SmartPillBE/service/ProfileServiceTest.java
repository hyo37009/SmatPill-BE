package com.example.SmartPillBE.service;

import com.example.SmartPillBE.domain.Profile;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProfileServiceTest {

    @Autowired ProfileService profileService;
    @Autowired
    EntityManager em;
//    @Test
//    public void 프로필생성조회() throws Exception {
//        // given
//        int profileId1 = profileService.newProfile("김희연", "2000-02-22", 167.8f, 65.2f, "여", "희연이");
//        int profileId2 = profileService.newProfile("최정원", "2000-01-24", 172.8f, 58.4f, "여", "가든");
//        int profileId3 = profileService.newProfile("김건우", "2003-10-14", 175.8f, 62.7f, "남", "동생");
//
//
//        // when
//        Profile profile1 = profileService.getProfile(profileId1);
//        Profile profile2 = profileService.getProfile(profileId2);
//
//        // then
//        assertThat(profile1.isRepresentative()).isTrue();
//        assertThat(profile2.isRepresentative()).isFalse();
//
//    }

}