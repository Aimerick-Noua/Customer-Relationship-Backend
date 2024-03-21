package com.example.crm.service;

import com.example.crm.model.ImageModel;
import com.example.crm.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;
    public ImageModel storeProfileImage(MultipartFile profilePicture) throws IOException {
        byte[] imageBytes = profilePicture.getBytes();

        String originalFilename = profilePicture.getOriginalFilename();
        String contentType = profilePicture.getContentType();
        ImageModel imageModel = new ImageModel(originalFilename, contentType, imageBytes);
        return imageRepository.save(imageModel);
    }

    public void deleteImage(Long id) {
        imageRepository.deleteById(id);
    }
}