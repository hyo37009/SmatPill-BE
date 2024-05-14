package com.example.SmartPillBE;

import com.example.SmartPillBE.domain.Pill;
import com.example.SmartPillBE.domain.Profile;
import com.example.SmartPillBE.repository.PillRepository;
import com.example.SmartPillBE.service.ProfileService;
import jakarta.persistence.EntityManager;
import org.aspectj.lang.annotation.After;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class dbTest {
    @Autowired
    ProfileService profileService;

    @Autowired
    PillRepository pillRepository;

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

    @Test
    public void 약이름으로조회() throws Exception {
        // given
        List<String> texts; // 글자들만 남도록 가공한 String 리스트
        List<Pill> searchedPills = new ArrayList<>();

        for (String text : texts) {
            List<Pill> pills = pillRepository.findByName(text);
            if(!pills.isEmpty()){
               searchedPills.addAll(pills);
            }
        }

        // when

        // then

    }


    @AfterEach
    void after(){

    }

    

}
