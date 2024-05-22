package com.example.SmartPillBE.service;

import com.example.SmartPillBE.domain.MedInfo;
import com.example.SmartPillBE.domain.Pill;
import com.example.SmartPillBE.domain.Profile;
import com.example.SmartPillBE.domain.doaseTime.DosageTime;
import com.example.SmartPillBE.domain.doaseTime.medPeriod;
import com.example.SmartPillBE.domain.doaseTime.medTiming;
import com.example.SmartPillBE.exception.IllegalPillException;
import com.example.SmartPillBE.exception.IllegalProfileException;
import com.example.SmartPillBE.repository.MedInfoRepository;
import com.example.SmartPillBE.repository.PillRepository;
import com.example.SmartPillBE.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MedInfoService {

    private final MedInfoRepository medInfoRepository;
    private final ProfileRepository profileRepository;
    private final PillRepository pillRepository;

    public MedInfo findOne(Long id){
        return medInfoRepository.findOne(id);
    }

    @Transactional
    public Long createMedInfo(int profileId, String pillNumber,
                              int howLong, String startDate,
                              String period, String timing,
                              int min, String eatTime) throws Exception {
        Profile profile = profileRepository.findOne(profileId);

        if(profile == null){
            throw new IllegalPillException("프로필 아이디가 올바르지 않습니다.");
        }

        Pill pill = pillRepository.findByNumber(pillNumber);

        if(pill == null){
            throw new IllegalProfileException("존재하지 않는 약번호입니다.");
        }

        LocalDate stDate = LocalDate.parse(startDate, DateTimeFormatter.ISO_DATE);
        eatTime += ":00"; // 포맷팅 단순화를 위해 뒤에 초를 임의로 붙여줌
        LocalTime eatTimeParse = LocalTime.parse(eatTime);
        DosageTime dosageTime = getDosageTime(period, timing, min);


        MedInfo medInfo = MedInfo.createMedInfo(pill, profile, howLong, stDate, dosageTime, eatTimeParse);
        medInfoRepository.save(medInfo);
        return medInfo.getId();
    }

    private DosageTime getDosageTime(String period, String timing, int min) throws Exception {
        DosageTime dosageTime = new DosageTime();
        dosageTime.setMedPeriod(parseMedPeriod(period));
        dosageTime.setMedTiming(parseMedTiming(timing));
        dosageTime.setTime_min(min);
        return dosageTime;
    }

    public List<MedInfo> findByPfIdAndPeriod(int profileId, String medPeriod) throws Exception {
        Profile profile = profileRepository.findOne(profileId);
        return medInfoRepository.findByProfileAndPeriod(profile, parseMedPeriod(medPeriod));
    }

    public List<MedInfo> findByProfileId(int profileId){
        return medInfoRepository.findByProfileId(profileId);
    }

    @Transactional
    public void setAlarm(Long id, String alarmTime){
        MedInfo medInfo = medInfoRepository.findOne(id);
        LocalTime time = LocalTime.parse(alarmTime + ":00");
        medInfo.setAlarm(time);
    }

    @Transactional
    public void cancelAlarm(Long id){
        MedInfo medInfo = medInfoRepository.findOne(id);
        medInfo.cancelAlarm();
    }

    @Transactional
    public void setCheck(Long id){
        MedInfo medInfo = medInfoRepository.findOne(id);
        medInfo.setStatus(true);
    }

    @Transactional
    public void cancelCheck(Long id){
        MedInfo medInfo = medInfoRepository.findOne(id);
        medInfo.setStatus(false);
    }

    private medPeriod parseMedPeriod(String period) throws Exception {
        return switch (period.toUpperCase()) {
            case "아침", "MORNING" -> medPeriod.MORNING;
            case "점심", "LUNCH" -> medPeriod.LUNCH;
            case "저녁", "DINNER" -> medPeriod.DINNER;
            case "자기전", "BEFORE_BED" -> medPeriod.BEFORE_BED;
            case "기타", "ECT" -> medPeriod.ECT;
            default -> throw new Exception("period의 형식이 올바르지 않습니다 : " + period);
        };
    }

    private medTiming parseMedTiming(String timing) throws Exception {
        return switch (timing) {
            case "전", "BEFORE" -> medTiming.BEFORE;
            case "후", "AFTER" -> medTiming.AFTER;
            case "상관없음", "NOT_CARE" -> medTiming.NOT_CARE;
            case "기타", "ECT" -> medTiming.ECT;
            default -> throw new Exception("timing의 형식이 올바르지 않습니다 : " + timing);
        };
    }
}
