package com.example.SmartPillBE.entity;

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
