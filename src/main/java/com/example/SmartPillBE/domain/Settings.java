package com.example.SmartPillBE.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
@Entity
public class Settings {
    @Id
    @GeneratedValue
    @Column(name = "settings_id")
    private Long id;
}
