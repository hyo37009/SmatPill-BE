package com.example.SmartPillBE.api;

import com.example.SmartPillBE.domain.SideEffect;
import com.example.SmartPillBE.service.SideEffectService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SideEffectApiController {

    private final SideEffectService sideEffectService;

    @GetMapping("/api/profiles/{id}/sideEffects")
    public List<SideEffectDto> getAll(@PathVariable("id") int profileId) throws Exception {
        return sideEffectService.findByProfileId(profileId).stream()
                .map(s -> new SideEffectDto(
                        s.getId(),
                        s.getPill().getPillNumber(),
                        s.getMemo()
                )).collect(Collectors.toList());
    }

    @GetMapping("/api/profiles/{id}/sideEffects/{pillNumber}")
    public GeneralResponse getMemo(@PathVariable("id") int profileId, @PathVariable("pillNumber") String pillNumber, @RequestBody String memo) throws Exception {
        SideEffect sideEffect = sideEffectService.findByProfileIdAndPillNumber(profileId, pillNumber);
        sideEffectService.setMemo(sideEffect.getId(), memo);
        return new GeneralResponse(sideEffect.getId().toString(), "정상적으로 설정되었습니다.");
    }

    @Transactional
    @PostMapping("/api/profiles/{id}/sideEffects/{pillNumber}")
    public GeneralResponse setMemo(@PathVariable("id") int profileId, @PathVariable("pillNumber") String pillNumber, @RequestBody String memo) throws Exception {
        SideEffect sideEffect = sideEffectService.findByProfileIdAndPillNumber(profileId, pillNumber);
        Long id = sideEffect.getId();
        sideEffectService.setMemo(id, memo);
        return new GeneralResponse(id.toString(), "정상적으로 수정되었습니다.");
    }

    @Transactional
    @PutMapping("/api/profiles/{id}/sideEffects/{pillNumber}")
    public GeneralResponse createOne(@PathVariable("id") int profileId, @PathVariable("pillNumber") String pillNumber, @RequestBody String memo) throws Exception {
        Long id = sideEffectService.create(profileId, pillNumber, memo);
        return new GeneralResponse(id.toString(), "정상적으로 생성되었습니다.");
    }

    @Transactional
    @DeleteMapping("/api/profiles/{id}/sideEffects/{pillNumber}")
    public GeneralResponse delete(@PathVariable("id") int profileId, @PathVariable("pillNumber") String pillNumber) throws Exception {
        SideEffect sideEffect = sideEffectService.findByProfileIdAndPillNumber(profileId, pillNumber);
        sideEffectService.delete(sideEffect.getId());
        return new GeneralResponse(null, "정상적으로 삭제되었습니다.");
    }

    @Data
    @AllArgsConstructor
    static class SideEffectDto {
        Long id;
        String pillNumber;
        String memo;
    }
}
