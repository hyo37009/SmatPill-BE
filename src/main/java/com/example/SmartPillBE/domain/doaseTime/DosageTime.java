package com.example.SmartPillBE.domain.doaseTime;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class DosageTime {
    private time_1 time_big; // 아침 점심 저녁 자기전 기타
    private time_2 time_small; // 식전 식후 전 후
    private int time_min; // 30분
}
