package com.example.SmartPillBE.photo.controller;

import com.example.SmartPillBE.api.GeneralResponse;
import com.example.SmartPillBE.domain.Profile;
import com.example.SmartPillBE.photo.dto.ImageDto;
import com.example.SmartPillBE.photo.service.S3ImageService;
import com.example.SmartPillBE.photo.service.S3Service;
import com.example.SmartPillBE.service.ProfileService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class S3ApiController {
    private final ProfileService profileService;
    private final S3Service s3Service;
    private final S3ImageService s3ImageService;


    @PutMapping("/api/profiles/{id}/profilePhoto")
    public GeneralResponse setProfilePhoto(@PathVariable("id") int id, MultipartFile image) throws Exception {
        String url = s3Service.uploadFile(image);

        ImageDto imageDto = new ImageDto(profileService.getProfile(id), url, image);
        s3ImageService.setProfileImg(imageDto);

        return new GeneralResponse(imageDto.getUrl(), "정상적으로 업로드되었습니다.");
    }

    @GetMapping("/api/profiles/{id}/profilePhoto")
    public String getProfilePhotoUrl(@PathVariable("id") int id) throws Exception {
        return profileService.getProfile(id).getProfileImg().getS3Url();
    }

    @DeleteMapping("/api/profiles/{id}/profilePhoto")
    public GeneralResponse deleteProfilePhotoUrl(@PathVariable("id") int id) throws Exception {
        s3ImageService.deleteProfileImg(id);
        return new GeneralResponse(null, "정상적으로 삭제되었습니다.");

    }

    @PutMapping("/api/profiles/{id}/prescriptions")
    public GeneralResponse putPrescriptions(@PathVariable("id") int id, MultipartFile image) throws Exception {
        Profile profile = profileService.getProfile(id);
        String url = s3Service.uploadFile(image);

        Long imageId = s3ImageService.savePrescription(new ImageDto(profile, url, image));

        return new GeneralResponse(String.valueOf(imageId), "정상적으로 업로드되었습니다.");
    }

    @GetMapping("/api/profiles/{id}/prescriptions")
    public List<S3ResponseDto> getPrescriptionUrls(@PathVariable("id") int id) throws Exception {
        return s3ImageService.getPrescriptions(id)
                .stream()
                .map(s -> new S3ResponseDto(
                        s.getId(),
                        s.getS3Url()
                )).collect(Collectors.toList());
    }

    @DeleteMapping("/api/profiles/{id}/prescriptions/{imgId}")
    public GeneralResponse deletePrescription(@PathVariable("id") int id,@PathVariable("imgId") Long imgid) throws Exception {
        s3ImageService.deletePrescription(id, imgid);
        return new GeneralResponse(null, "정상적으로 삭제되었습니다.");
    }

    @Data
    @AllArgsConstructor
    private static class S3ResponseDto {
        Long id;
        String url;
    }
}
