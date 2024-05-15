package com.example.SmartPillBE.api;

import com.example.SmartPillBE.domain.Pill;
import com.example.SmartPillBE.service.PillService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PillApiController {

    private final PillService pillService;

    @GetMapping("/api/pill/{pillNumber}")
    public Pill searchByNumber(@PathVariable("pillNumber") String pillNumber){
        return pillService.findByNumber(pillNumber);
    }

    @GetMapping("/api/pill/name/{pillName}")
    public List<Pill> searchByName(@PathVariable("pillName") String pillName){
        return pillService.findByName(pillName);
    }
}
