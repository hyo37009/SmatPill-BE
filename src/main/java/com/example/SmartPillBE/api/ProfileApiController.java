package com.example.SmartPillBE.api;

import com.example.SmartPillBE.domain.Profile;
import com.example.SmartPillBE.service.ProfileService;
import jakarta.annotation.Nullable;
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
    public Result findAll(@PathVariable("id") int id) throws Exception {
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
    public ProfileResponse createProfile(@RequestBody CreateProfileRequest request) {
        int newProfileId = profileService.newProfile(
                request.name,
                request.birth,
                request.height,
                request.weight,
                request.sex,
                request.nickname);
        return new ProfileResponse(newProfileId, "정상적으로 생성되었습니다.");
    }

    @PostMapping("/api/profiles/{id}")
    public ProfileResponse updateProfile(@PathVariable("id") int id, @RequestBody UpdateProfileRequest request) throws Exception {
        Profile profile = profileService.getProfile(id);
        profileService.updateProfile(id, request.name, request.height, request.weight, request.nickname);

        return new ProfileResponse(profile.getId(), "정상적으로 수정되었습니다.");
    }

    @DeleteMapping("/api/profiles/{id}")
    public ProfileResponse deleteProfile(@PathVariable("id") int id) throws Exception {
        profileService.deleteProfile(id);
        return new ProfileResponse(id, "정상적으로 삭제되었습니다.");
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
    }

    @Data
    @AllArgsConstructor
    static class ProfileResponse {
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

    @Data
    @Nullable
    static class UpdateProfileRequest {
        private String name;
        private float height;
        private float weight;
        private String nickname;
    }
}
