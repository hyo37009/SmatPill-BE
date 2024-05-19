package com.example.SmartPillBE.api;

import com.example.SmartPillBE.domain.Pill;
import com.example.SmartPillBE.service.PillService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PillApiController {

    private final PillService pillService;

    @GetMapping("/api/pill/{pillNumber}")
    public PillResponseForApp searchByNumber(@PathVariable("pillNumber") String pillNumber){
        Pill pill = pillService.findByNumber(pillNumber);
        if(pill == null){
            return null;
        }
        return new PillResponseForApp(pill);
    }

    @GetMapping("/api/pill/name/{pillName}")
    public List<PillResponseForApp> searchByName(@PathVariable("pillName") String pillName){
        List<Pill> pills = pillService.findByName(pillName);
        return pills.stream()
                .map(PillResponseForApp::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/api/pill/print/{print}")
    public List<PillResponseForAiModel> searchByPrint(@PathVariable("print") String print){
        List<Pill> pills = pillService.findByPrint(print);
        return pills.stream()
                .map(PillResponseForAiModel::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/api/pill/shape/{shape}")
    public List<PillResponseForAiModel> searchByShape(@PathVariable("shape") String shape){
        List<Pill> pills = pillService.findByShape(shape);
        return pills.stream()
                .map(PillResponseForAiModel::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/api/pill/line/{line}")
    public List<PillResponseForAiModel> searchByLine(@PathVariable("line") String line){
        List<Pill> pills = pillService.findByLine(line);
        return pills.stream()
                .map(PillResponseForAiModel::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/api/pill/color/{color}")
    public List<PillResponseForAiModel> searchByColor(@PathVariable("color") String color){
        List<Pill> pills = pillService.findByColor(color);
        return pills.stream()
                .map(PillResponseForAiModel::new)
                .collect(Collectors.toList());
    }

    @Data
    private static class PillResponseForApp{
        private String pillNumber;
        private String pillName;
        private String dosageForm;
        private String effect;
        private String category;
        private String imagePath;

        private PillResponseForApp(Pill pill){
            this.pillName = pill.getPillName();
            this.pillNumber = pill.getPillNumber();
            this.dosageForm = pill.getDosageForm();
            this.effect = pill.getEffect();
            this.category = pill.getCategory();
            this.imagePath = pill.getImagePath();
        }

    }

    @Data
    private static class PillResponseForAiModel{
        private String pillNumber;
        private String pillName;
        private String printFront;
        private String printBack;
        private String shape;
        private String colorFront;
        private String colorBack;
        private String lineFront;
        private String lineBack;

        private PillResponseForAiModel(Pill pill){
            this.pillNumber = pill.getPillNumber();
            this.pillName = pill.getPillName();
            this.printFront = pill.getPrintFront();
            this.printBack = pill.getPrintBack();
            this.shape = pill.getShape();
            this.colorFront = pill.getColorFront();
            this.colorBack = pill.getColorBack();
        }

    }



}
