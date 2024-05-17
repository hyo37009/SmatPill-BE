package com.example.SmartPillBE.repository;

import com.example.SmartPillBE.domain.Pill;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@SpringBootTest
class PillRepositoryTest {

    @Autowired PillRepository pillRepository;

    @Test
    public void 이름_조회_테스트() throws Exception {
        // given
        String pillname = "타이레놀";

        // when
        List<Pill> pills = pillRepository.findByName(pillname);

        // then
        for (Pill pill : pills) {
            System.out.println("pill = " + pill.getPillName());
        }

    }

    @Test
    public void 번호_조회_테스트() throws Exception {
        // given
        String pillNumber = "200402588";
        // when
        Pill pill = pillRepository.findByNumber(pillNumber);

        // then
        System.out.println("pill = " + pill.getPillName());

    }

}