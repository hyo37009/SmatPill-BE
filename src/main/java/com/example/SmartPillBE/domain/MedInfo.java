package com.example.SmartPillBE.domain;

import com.example.SmartPillBE.domain.doaseTime.DosageTime;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Table(name = "med_info")
public class MedInfo {
    @Id
    @GeneratedValue
    @Column(name = "med_info_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pill_number")
    private Pill pill;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    private Profile profile;

    private int howLong;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @Embedded
    private DosageTime dosageTime; // 복용법 아침 식전 30분

    private LocalTime eatTime; // 복용 시간

    private boolean setAlarm = false;

    private LocalTime alarmTime;


    // 연관관계 메서드 //

    public static MedInfo createMedInfo(Pill pill, Profile profile,
                   int howLong, LocalDate startDate,
                   DosageTime dosageTime, LocalTime eatTime) {
        MedInfo medInfo = new MedInfo();

        medInfo.pill = pill;
        medInfo.profile = profile;
        medInfo.howLong = howLong;
        medInfo.startDate = startDate;
        medInfo.dosageTime = dosageTime;
        medInfo.eatTime = eatTime;

        return medInfo;
    }



    // 비즈니스 로직 //

    /**
     * 알람 설정
     */
    public void setSetAlarm(LocalTime alarmTime) {
        if(setAlarm){
            throw new IllegalStateException("알람이 이미 설정되어있습니다.");
        }

        this.setAlarm = true;
        this.alarmTime = alarmTime;
    }

    public void cancelAlarm(){
        this.setAlarm = false;
    }
}
