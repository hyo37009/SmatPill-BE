package com.example.SmartPillBE.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
public class SideEffect {
    @Id
    @GeneratedValue
    @Column(name = "sideEffect_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    private Profile profile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="pill_id")
    private Pill pill;

    @Setter
    @Column
    private String memo;

    public static SideEffect createSideEffect(Profile profile, Pill pill, String memo){
        SideEffect sideEffect = new SideEffect();
        sideEffect.profile = profile;
        sideEffect.pill = pill;
        sideEffect.memo = memo;
        return sideEffect;
    }


}
