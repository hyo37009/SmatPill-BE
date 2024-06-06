package com.example.SmartPillBE.photo.controller;

import com.example.SmartPillBE.api.GeneralResponse;
import com.example.SmartPillBE.domain.Profile;
import com.example.SmartPillBE.photo.domain.S3Image;
import com.example.SmartPillBE.photo.dto.ImageDto;
import com.example.SmartPillBE.photo.service.S3ImageService;
import com.example.SmartPillBE.photo.service.S3Service;
import com.example.SmartPillBE.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
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

    @PutMapping("/api/profiles/{id}/prescriptions")
    public GeneralResponse putPrescriptions(@PathVariable("id") int id, MultipartFile image) throws Exception {
        Profile profile = profileService.getProfile(id);
        String url = s3Service.uploadFile(image);

        Long imageId = s3ImageService.savePrescription(new ImageDto(profile, url, image));

        return new GeneralResponse(String.valueOf(imageId), "정상적으로 업로드되었습니다.");
    }

    @GetMapping("/api/profiles/{id}/prescriptions")
    public List<String> getPrescriptionUrls(@PathVariable("id") int id) throws Exception {
        return s3ImageService.getPrescriptions(id)
                .stream()
                .map(S3Image::getS3Url)
                .collect(Collectors.toList());
    }

}
