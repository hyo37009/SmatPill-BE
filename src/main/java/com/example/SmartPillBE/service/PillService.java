package com.example.SmartPillBE.service;

import com.example.SmartPillBE.domain.Pill;
import com.example.SmartPillBE.repository.PillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PillService {

    private final PillRepository pillRepository;

    public List<Pill> findByName(String pillName){
        return pillRepository.findByName(pillName);
    }

    public Pill findByNumber(String pillNumber){
        return pillRepository.findByNumber(pillNumber);
    }
}
