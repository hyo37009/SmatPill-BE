package com.example.SmartPillBE.domain.doaseTime;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter @Setter
public class DosageTime {
    private medPeriod medPeriod; // 아침 점심 저녁 자기전 기타
    private medTiming medTiming; // 식전 식후
    private int time_min; // 30분

}
