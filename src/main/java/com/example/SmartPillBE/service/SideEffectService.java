package com.example.SmartPillBE.service;

import com.example.SmartPillBE.domain.Pill;
import com.example.SmartPillBE.domain.Profile;
import com.example.SmartPillBE.domain.SideEffect;
import com.example.SmartPillBE.repository.SideEffectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SideEffectService {

    private final SideEffectRepository sideEffectRepository;
    private final PillService pillService;
    private final ProfileService profileService;


    public Long create(int profileId, String pillNumber, String memo) throws Exception {
        Profile profile = profileService.getProfile(profileId);
        Pill pill = pillService.findByNumber(pillNumber);

        SideEffect sideEffect = sideEffectRepository.findByProfileAndPill(profile, pill);
        if (sideEffect != null){
            sideEffectRepository.delete(sideEffect);
        }
        SideEffect newSideEffect = SideEffect.createSideEffect(profile, pill, memo);
        sideEffectRepository.save(newSideEffect);
        return newSideEffect.getId();
    }
    public SideEffect findOne(Long id) throws Exception {
        SideEffect sideEffect = sideEffectRepository.findOne(id);
        if(sideEffect == null){
            throw new Exception("존재하지 않는 부작용 id입니다.");
        }
        return sideEffect;
    }
    public List<SideEffect> findByProfileId(int profileId) throws Exception {
        Profile profile = profileService.getProfile(profileId);
        return sideEffectRepository.findByProfile(profile);
    }

    public SideEffect findByProfileIdAndPillNumber(int profileId, String pillNumber) throws Exception {
        Profile profile = profileService.getProfile(profileId);
        Pill pill = pillService.findByNumber(pillNumber);
        return sideEffectRepository.findByProfileAndPill(profile, pill);
    }

    public Long setMemo(Long id, String memo){
        SideEffect sideEffect = sideEffectRepository.findOne(id);
        sideEffect.setMemo(memo);
        return sideEffect.getId();
    }

    public void delete(Long id){
        SideEffect sideEffect = sideEffectRepository.findOne(id);
        sideEffectRepository.delete(sideEffect);
    }

}
