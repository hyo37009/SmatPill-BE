package com.example.SmartPillBE.service;

import com.example.SmartPillBE.domain.MedicalHistory;
import com.example.SmartPillBE.domain.Profile;
import com.example.SmartPillBE.repository.MedicalHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MedicalHistoryService {

    private final ProfileService profileService;
    private final MedicalHistoryRepository medicalHistoryRepository;

    @Transactional
    public Long create(int profileId, String disease, LocalDate diagnosis) throws Exception {
        Profile profile = profileService.getProfile(profileId);
        MedicalHistory medicalHistory = MedicalHistory.createMedicalHistory(profile, disease, diagnosis);
        medicalHistoryRepository.save(medicalHistory);
        return medicalHistory.getId();
    }

    @Transactional
    public void setMemo(Long id, String memo){
        MedicalHistory medicalHistory = medicalHistoryRepository.findById(id);
        medicalHistory.setMemo(memo);
    }

    public List<MedicalHistory> getByProfile(int profileId) throws Exception {
        Profile profile = profileService.getProfile(profileId);
        return medicalHistoryRepository.getByProfile(profile);
    }

    public void delete(Long id) {
        MedicalHistory medicalHistory = medicalHistoryRepository.findById(id);
        medicalHistoryRepository.delete(medicalHistory);
    }
}
