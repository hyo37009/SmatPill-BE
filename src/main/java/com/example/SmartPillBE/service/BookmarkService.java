package com.example.SmartPillBE.service;

import com.example.SmartPillBE.domain.Pill;
import com.example.SmartPillBE.repository.PillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BookmarkService {
    private final PillRepository pillRepository;

    void fun(){
        List<String> resultStrs;


        List<Pill> pills = pillRepository.findByName(name);
    }
}
