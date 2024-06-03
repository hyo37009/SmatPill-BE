package com.example.SmartPillBE.api;

import com.example.SmartPillBE.domain.MedInfo;
import com.example.SmartPillBE.service.MedInfoService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Transactional
@RequiredArgsConstructor
public class MedInfoApiController {

    private final MedInfoService medInfoService;

    @GetMapping("/api/profiles/{id}/medInfo")
    public Result findAllById(@PathVariable("id") int id) {
        List<MedInfo> medInfos = medInfoService.findByProfileId(id);
        return convertToDto(medInfos);
    }

    @PutMapping("/api/profiles/{id}/medInfo")
    public GeneralResponse createMedInfo(@PathVariable("id") int profileId, @RequestBody CreateMedInfoRequest request) throws Exception {
        List<Long> medInfoIdList = medInfoService.createMedInfo(
                profileId,
                request.pillNumber,
                request.howLong,
                request.startDate,
                request.period,
                request.timing,
                request.min,
                request.eatTime);
        return new GeneralResponse(null, "생성되었습니다.");
    }

    @PostMapping("/api/profiles/{id}/medInfo/{medInfoId}/alarm/{time}")
    public MedInfoResponse setAlarm(@PathVariable("medInfoId") Long id, @PathVariable("time") String time) throws Exception {
        if (!time.matches("(2[0-3]|[01][0-9]):[0-5][0-9]")) {
            throw new Exception("시간 형식이 잘못되었습니다 : " + time);
        }
        medInfoService.setAlarm(id, time);
        return new MedInfoResponse(id, "알람이 정상적으로 생성되었습니다.");
    }

    @DeleteMapping("/api/profiles/{id}/medInfo/{medInfoId}/alarm")
    public MedInfoResponse cancelAlarm(@PathVariable("medInfoId") Long id) {
        medInfoService.cancelAlarm(id);
        return new MedInfoResponse(id, "알람이 정상적으로 해제되었습니다.");
    }
    @GetMapping("/api/profiles/{id}/medInfo/{medInfoId}/alarm")
    public boolean isAlarmSet( @PathVariable("medInfoId") Long id){
        return medInfoService.isAlarmSet(id);
    }


    @PutMapping("/api/profiles/{id}/medInfo/{medInfoId}/check")
    public MedInfoResponse check(@PathVariable("medInfoId") Long id) throws Exception {
        medInfoService.setCheck(id);
        return new MedInfoResponse(id, "복용이 체크되었습니다.");
    }



    @DeleteMapping("/api/profiles/{id}/medInfo/{medInfoId}/check")
    public MedInfoResponse cancelCheck(@PathVariable("medInfoId") Long id) {
        medInfoService.cancelCheck(id);
        return new MedInfoResponse(id, "복용 체크가 해제되었습니다.");
    }

    @GetMapping("/api/profiles/{id}/medInfo/{medInfoId}/check")
    public boolean isChecked(@PathVariable("medInfoId") Long id) {
        return medInfoService.isChecked(id);
    }

    @GetMapping("/api/profiles/{id}/medInfo/{date}/{period}")
    public Result findByDateAndPeriod(@PathVariable("id") int id, @PathVariable("date") String date, @PathVariable("period") String period) throws Exception {
        List<MedInfo> medInfos = medInfoService.findByDateAndPeriod(id, date, period);
        return convertToDto(medInfos);
    }

    private Result convertToDto(List<MedInfo> medInfos){
        List<MedInfoDto> collect = medInfos.stream()
                .map(m -> new MedInfoDto(
                        m.getId(),
                        m.getPill().getPillNumber(),
                        m.getProfile().getId(),
                        m.getDate().toString(),
                        m.getDosageTime().getMedPeriod().toString(),
                        m.getDosageTime().getMedTiming().toString(),
                        m.getDosageTime().getTime_min(),
                        m.isAlarmSet(),
                        m.getAlarmTime()!=null?m.getAlarmTime().toString():null,
                        m.isStatus()
                )).collect(Collectors.toList());
        return new Result(collect);
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }

    @Data
    @AllArgsConstructor
    static class MedInfoDto {
        private Long id;
        private String pillNumber;
//        private Pill pill;
        private int profileId;
//        private int howLong;
        private String startDate;
        private String period;
        private String timing;
        private int min;
        private boolean alarmSet;
        private String alarmTime = null;
        private boolean status = false;
    }

    @Data
    @AllArgsConstructor
    static class CreateMedInfoRequest {
        private String pillNumber;
        private int howLong;
        private String startDate;
        private String period;
        private String timing;
        private int min;
        private String eatTime;
    }

    @Data
    @AllArgsConstructor
    static class MedInfoResponse {
        private Long medInfoId;
        private String message;
    }

    @Data
    @AllArgsConstructor
    static class SetAlarmTime{
        private String alarmTime;
    }
}
