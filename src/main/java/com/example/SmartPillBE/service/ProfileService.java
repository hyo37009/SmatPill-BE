package com.example.SmartPillBE.service;

import com.example.SmartPillBE.domain.Profile;
import com.example.SmartPillBE.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ProfileService {

    @Autowired
    ProfileRepository profileRepository;

    @Transactional
    public int newProfile(Profile profile){
        profileRepository.save(profile);
        return profile.getId();
    }

    /**
     * 프로필 조회
     */
    public Profile getProfile(int profileId){
        return profileRepository.findOne(profileId);
    }

}
