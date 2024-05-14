package com.example.SmartPillBE;

import com.example.SmartPillBE.domain.Profile;
import com.example.SmartPillBE.service.ProfileService;
import jakarta.persistence.EntityManager;
import org.aspectj.lang.annotation.After;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class dbTest {
    @Autowired
    ProfileService profileService;

    @Autowired
    EntityManager em;

//    @Test
//    public void 프로필생성테스트() throws Exception {
//        // given
//        int profileId = profileService.newProfile("김건우", "2003-10-14", 175.2F, 65.2F, "남", "동생");
//
//        // when
//        Profile profile = profileService.getProfile(profileId);
//
//        // then
//        System.out.println(profile.toString());
//
//    }



    @AfterEach
    void after(){

    }

    

}
