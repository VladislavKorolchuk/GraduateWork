package ru.work.graduatework.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.work.graduatework.Entity.Ads;
import ru.work.graduatework.Entity.Image;
import ru.work.graduatework.dto.repository.AdsRepository;
import ru.work.graduatework.dto.repository.ImageRepository;
import ru.work.graduatework.service.ImageService;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
public class ImageServiceImpl implements ImageService {

    @Value("${path.to.image.folder}")
    private String imageDir;

    private final AdsRepository adsRepository;
    private final ImageRepository imageRepository;

    public ImageServiceImpl(AdsRepository adsRepository, ImageRepository imageRepository) {
        this.adsRepository = adsRepository;
        this.imageRepository = imageRepository;
    }

    public Image addImage(Integer id, MultipartFile imageFile) throws IOException {
        Ads ads = adsRepository.findById(id).orElseThrow();
        Path path = Path.of(imageDir, id + "." + getExtensions(
                Objects.requireNonNull(imageFile.getOriginalFilename())));
        if (!Files.exists(path.getParent())) {
            Files.createDirectory(path.getParent());
        }
        Files.deleteIfExists(path);
        try (
                InputStream is = imageFile.getInputStream();
                OutputStream os = Files.newOutputStream(path, CREATE_NEW);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
        ) {
            bis.transferTo(bos);
        }
        Image image = new Image();
        image.setFilePath(path.toString());
        image.setMediaType(imageFile.getContentType());
        image.setFileSize(imageFile.getSize());
        image.setData(imageFile.getBytes());
        image.setAds(ads);
        imageRepository.save(image);
        return image;
    }

    private String getExtensions(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }
}