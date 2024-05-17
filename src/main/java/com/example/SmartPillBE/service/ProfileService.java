package com.example.SmartPillBE.service;

import com.example.SmartPillBE.domain.Gender;
import com.example.SmartPillBE.domain.Profile;
import com.example.SmartPillBE.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class ProfileService {

    @Autowired
    ProfileRepository profileRepository;

    @Transactional
    public int newProfile(String name, String birth, float height, float weight, String sex, String nickname){
        System.out.println("birth = " + birth);
        LocalDate birthDate = LocalDate.parse(birth, DateTimeFormatter.ISO_LOCAL_DATE);
        Gender gender = (sex == "여")?Gender.FEMALE:Gender.MALE;

        Profile profile = Profile.createProfile(name, birthDate, height, weight, gender, nickname);
        if (profileRepository.isEmpty()) {
            profile.setRepresentative();
        }
        profileRepository.save(profile);
        return profile.getId();
    }

    /**
     * 프로필 조회
     */
    public Profile getProfile(int profileId){
        return profileRepository.findOne(profileId);
    }

    public List<Profile> findAll(){
        return profileRepository.findAll();
    }

}
