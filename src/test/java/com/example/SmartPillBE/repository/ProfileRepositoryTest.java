package com.example.SmartPillBE.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ProfileRepositoryTest {
    @Autowired ProfileRepository profileRepository;

    @Test
    void countAll() {
        System.out.println(profileRepository.countAll());
    }

}