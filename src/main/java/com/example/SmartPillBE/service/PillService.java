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

    public List<Pill> findByShape(String shape){
        return pillRepository.findByShape(shape);
    }

    public List<Pill> findByLine(String line){
        return pillRepository.findByLine(line);
    }

    public List<Pill> findByColor(String color){
        return pillRepository.findByColor(color);
    }

    public List<Pill> findByPrint(String print){
        return pillRepository.findByPrint(print);
    }

    public boolean hasLine(Pill pill){
        return !(pill.getLineBack().isEmpty() && pill.getLineFront().isEmpty());
    }
}
