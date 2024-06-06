package com.example.SmartPillBE.photo.dto;

import com.example.SmartPillBE.domain.Profile;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
public class ImageDto {
    private Profile profile;
    private String url;
    private MultipartFile file;
}
