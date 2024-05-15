package com.example.SmartPillBE.domain;

import com.example.SmartPillBE.domain.doaseRegimen.DosageRegimen;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "pill")
public class Pill {
    @Id
    @Column(name = "pill_number", unique = true, nullable = false)
    private String pillNumber;

    private String pillName;

    private String dosageForm;

    private String effect;

    private String category;

    private String imagePath;


}
