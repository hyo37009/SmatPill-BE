package com.example.SmartPillBE.api;

import com.example.SmartPillBE.service.MedicalHistoryService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MedicalHistoryApiController {
    private final MedicalHistoryService medicalHistoryService;

    @GetMapping("/api/profiles/{id}/medicalHistories")
    public List<MedicalHistoryDto> getAllById(@PathVariable("id") int profileId) throws Exception {
        return medicalHistoryService.getByProfile(profileId).stream()
                .map(m -> new MedicalHistoryDto(
                        m.getId(),
                        m.getProfile().getId(),
                        m.getDisease(),
                        m.getDiagnosis().format(DateTimeFormatter.ISO_LOCAL_DATE),
                        m.getMemo()
                )).collect(Collectors.toList());
    }

    @PutMapping("/api/profiles/{id}/mediclaHistories")
    public GeneralResponse create(@PathVariable("id") int profileId, @RequestBody CreateMediHisRequest request) throws Exception {
        LocalDate date = LocalDate.parse(request.diagnosis, DateTimeFormatter.ISO_LOCAL_DATE);
        Long id = medicalHistoryService.create(profileId, request.disease, date);
        return new GeneralResponse(id, "정상적으로 생성되었습니다.");
    }


    @PostMapping("/api/profiles/{id}/medicalHistories/{historyId}")
    public GeneralResponse modifyMemo(@PathVariable("historyId") Long id, @RequestBody String memo){
        medicalHistoryService.setMemo(id, memo);
        return new GeneralResponse(id, "정상적으로 수정되었습니다.");
    }

    @DeleteMapping("/api/profiles/{id}/medicalHistories/{historyId}")
    public GeneralResponse delete(@PathVariable("historyId") Long id){
        medicalHistoryService.delete(id);
        return new GeneralResponse(null, "정상적으로 삭제되었습니다.");
    }



    @Data
    @AllArgsConstructor
    static class MedicalHistoryDto {
        Long id;
        int profileId;
        String disease;
        String diagnosis;
        String memo;
    }

    @Data
    private class CreateMediHisRequest {
        String disease;
        String diagnosis;
        String memo;
    }
}
