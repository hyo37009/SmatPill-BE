package com.example.SmartPillBE.domain;

import com.example.SmartPillBE.domain.doaseTime.DosageTime;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
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

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @Embedded
    private DosageTime dosageTime; // 복용법 아침 식전 30분

    private LocalTime eatTime; // 복용 시간

    private boolean alarmSet;

    private LocalTime alarmTime;

    @Setter
    private boolean status; // 복용여부


    // 연관관계 메서드 //

    public static MedInfo createMedInfo(Pill pill, Profile profile,
                   LocalDate startDate,
                   DosageTime dosageTime, LocalTime eatTime) {
        MedInfo medInfo = new MedInfo();

        medInfo.pill = pill;
        medInfo.profile = profile;
        medInfo.date = startDate;
        medInfo.dosageTime = dosageTime;
        medInfo.eatTime = eatTime;

        medInfo.status = false;
        medInfo.alarmSet = false;
        medInfo.alarmTime = null;

        return medInfo;
    }



    // 비즈니스 로직 //

    /**
     * 알람 설정
     */
    public void setAlarm(LocalTime alarmTime) {
        this.alarmSet = true;
        this.alarmTime = alarmTime;
    }

    public void cancelAlarm(){
        this.alarmSet = false;
        this.alarmTime = null;
    }

}
