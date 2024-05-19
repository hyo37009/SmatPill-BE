package com.example.SmartPillBE.service;

import com.example.SmartPillBE.domain.MedInfo;
import com.example.SmartPillBE.domain.Pill;
import com.example.SmartPillBE.domain.Profile;
import com.example.SmartPillBE.domain.doaseTime.DosageTime;
import com.example.SmartPillBE.domain.doaseTime.medPeriod;
import com.example.SmartPillBE.domain.doaseTime.medTiming;
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

    @Transactional
    public Long createMedInfo(int profileId, String pillNumber,
                              int howLong, String startDate,
                              List<String> timeArr, String time) throws Exception {
        Profile profile = profileRepository.findOne(profileId);
        Pill pill = pillRepository.findByNumber(pillNumber);
        LocalDate stDate = LocalDate.parse(startDate, DateTimeFormatter.ISO_DATE);
        time += ":00"; // 포맷팅 단순화를 위해 뒤에 초를 임의로 붙여줌
        LocalTime eatTime = LocalTime.parse(time);
        DosageTime dosageTime = getDosageTime(timeArr);


        MedInfo medInfo = MedInfo.createMedInfo(pill, profile, howLong, stDate, dosageTime, eatTime);
        medInfoRepository.save(medInfo);
        return medInfo.getId();
    }

    private DosageTime getDosageTime(List<String> timeArr) throws Exception {
        DosageTime dosageTime = new DosageTime();
        dosageTime.setMedPeriod(parseMedPeriod(timeArr.get(0)));
        dosageTime.setMedTiming(parseMedTiming(timeArr.get(1)));
        dosageTime.setTime_min(Integer.parseInt(timeArr.get(2)));
        return dosageTime;
    }

    public List<MedInfo> findByPfIdAndPeriod(int profileId, String medPeriod){
        Profile profile = profileRepository.findOne(profileId);
        return medInfoRepository.findByProfileAndPeriod(profile, parseMedPeriod(medPeriod));
    }

    private medPeriod parseMedPeriod(String period){
        return switch (period) {
            case "아침", "MORNING" -> medPeriod.MORNING;
            case "점심", "LUNCH" -> medPeriod.LUNCH;
            case "저녁", "DINNER" -> medPeriod.DINNER;
            case "자기전", "BEFORE_BED" -> medPeriod.BEFORE_BED;
            case "기타", "ECT" -> medPeriod.ECT;
            default -> throw new IllegalStateException("Unexpected value: " + period);
        };
    }

    private medTiming parseMedTiming(String timing){
        return switch (timing) {
            case "전", "BEFORE" -> medTiming.BEFORE;
            case "후", "AFTER" -> medTiming.AFTER;
            case "상관없음", "NOT_CARE" -> medTiming.NOT_CARE;
            case "기타", "ECT" -> medTiming.ECT;
            default -> throw new IllegalStateException("Unexpected value: " + timing);
        };
    }
}
