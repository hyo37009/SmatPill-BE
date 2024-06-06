package com.example.SmartPillBE.photo.service;

import com.example.SmartPillBE.domain.Profile;
import com.example.SmartPillBE.photo.domain.S3Image;
import com.example.SmartPillBE.photo.dto.PrescriptionImageDto;
import com.example.SmartPillBE.photo.dto.ImageDto;
import com.example.SmartPillBE.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class S3ImageService {
    private final S3ImageRepository s3ImageRepository;
    private final ProfileService profileService;
    private final S3Service s3Service;

    /**
     * 처방전 사진을 저장하는 메서드
     * 1. 객체 생성
     * 2. persist
     * 3. profile에 저장
     */
    public Long savePrescription(ImageDto imageDto) throws Exception {
        S3Image newImage = new S3Image(imageDto.getProfile(), imageDto.getUrl());
        s3ImageRepository.save(newImage);

        return newImage.getId();
    }

    public List<S3Image> getPrescriptions(int profileId) throws Exception {
        Profile profile = profileService.getProfile(profileId);
        return s3ImageRepository.findByProfile(profile);
    }
//
//    public void deletePrescription(int profileId, S3Image s3Image) throws Exception {
//        Profile profile = profileService.getProfile(profileId);
//        profile.deletePrescription(s3Image);
//        s3ImageRepository.delete(s3Image);
//
//    }

    public Long setProfileImg(ImageDto imageDto) throws Exception {
        S3Image s3Image = new S3Image(imageDto.getProfile(), imageDto.getUrl());
        s3ImageRepository.save(s3Image);

        Profile profile = profileService.getProfile(imageDto.getProfile().getId());
        profile.setProfileImg(s3Image);

        return s3Image.getId();
    }

    public void deleteProfileImg(int profileId) throws Exception {
        Profile profile = profileService.getProfile(profileId);
        profile.deleteProfileImg();
    }


//    public FileEntity getFile(int profileId){
//        return profilePhotoRepository.getReferenceById(id);
//    }

}
