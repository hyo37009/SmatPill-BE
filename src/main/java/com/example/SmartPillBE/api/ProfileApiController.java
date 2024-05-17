package com.example.SmartPillBE.api;

import com.example.SmartPillBE.domain.Profile;
import com.example.SmartPillBE.service.ProfileService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ProfileApiController {
    private final ProfileService profileService;

    @GetMapping("/api/profiles/{id}")
    public Result findAll(@PathVariable("id") int id) {
        Profile profile = profileService.getProfile(id);
        return new Result<>(profile);
    }

    @GetMapping("/api/profiles")
    public Result findAll() {
        List<Profile> findProfiles = profileService.findAll();
        List<ProfileDto> collect = findProfiles.stream()
                .map(p -> new ProfileDto(
                        p.getId(),
                        p.getName(),
                        p.getBirth().format(DateTimeFormatter.ISO_LOCAL_DATE),
                        p.getHeight(),
                        p.getWeight(),
                        p.getSex().toString(),
                        p.getNickname(),
                        p.isRepresentative()))
                .collect(Collectors.toList());

        return new Result(collect);

    }

    @PutMapping("/api/profiles")
    public CreateProfileResponse saveProfile(@RequestBody CreateProfileRequest request) {
        System.out.println("request.toString() = " + request.toString());
        int newProfileId = profileService.newProfile(
                request.name,
                request.birth,
                request.height,
                request.weight,
                request.sex,
                request.nickname);
        return new CreateProfileResponse(newProfileId, "정상적으로 생성되었습니다.");
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }

    @Data
    @AllArgsConstructor
    static class ProfileDto {
        private int id;
        private String name;
        private String birth;
        private float height;
        private float weight;
        private String sex;
        private String nickname;
        private boolean isRepresentative;
//
//        public ProfileDto(String name, String birth, double height, double weight, String sex, String nickname) {
//            this.name = name;
//            this.birth = birth;
//            this.height = height;
//            this.weight = weight;
//            this.sex = sex;
//            this.nickname = nickname;
//        }
    }

    @Data
    @AllArgsConstructor
    static class CreateProfileResponse {
        private int id;
        private String message;
    }

    @Data
    static class CreateProfileRequest {
        private String name;
        private String birth;
        private float height;
        private float weight;
        private String sex;
        private String nickname;
        
        
    }
}
