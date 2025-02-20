package com.example.nhom3_tt_.services.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import com.example.nhom3_tt_.exception.AppException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.example.nhom3_tt_.dtos.requests.lecture.LectureVideoFolderStructure;
import com.example.nhom3_tt_.services.CloudinaryService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CloudinaryServiceImpl implements CloudinaryService {

  private final Cloudinary cloudinary;

  @Value("${cloudinary.application.name}")
  private String APPLICATION_NAME;

  @Override
  public String uploadVideo(MultipartFile file, LectureVideoFolderStructure request)
      throws IOException {
    String publicValue = generatePublicValue(file.getOriginalFilename());
    File fileUpload = convert(file);
    Map<String, Object> params = new HashMap<>();

    // create folder name in cloudinary
    String folder =
        String.join(
            "/",
            APPLICATION_NAME,
            request.getInstructorId().toString(),
            request.getCourseId().toString(),
            request.getSectionId().toString());
    // => instructor_id/course_id/section_id/video

    params.put("resource_type", "video");
    params.put("folder", folder);
    params.put("public_id", publicValue);

    Map<?, ?> uploadResult = cloudinary.uploader().upload(fileUpload, params);

    cleanDisk(fileUpload);

    return (String) uploadResult.get("url");
  }

  @Override
  public String uploadImage(MultipartFile file, LectureVideoFolderStructure request)
      throws IOException {
    String publicValue = generatePublicValue(file.getOriginalFilename());
    File fileUpload = convert(file);
    Map<String, Object> params = new HashMap<>();

    String folder =
        String.join(
            "/",
            APPLICATION_NAME,
            request.getInstructorId().toString(),
            request.getCourseId().toString(),
            request.getSectionId().toString());
    // => instructor_id/course_id/section_id/video

    params.put("folder", folder);
    params.put("public_id", publicValue);

    Map<?, ?> uploadResult = cloudinary.uploader().upload(fileUpload, params);

    cleanDisk(fileUpload);

    return (String) uploadResult.get("url");
  }

  @Override
  public String uploadImage(MultipartFile file) throws IOException {
    String publicValue = generatePublicValue(file.getOriginalFilename());
    File fileUpload = convert(file);
    Map<String, Object> params = new HashMap<>();

    params.put("public_id", publicValue);

    Map<?, ?> uploadResult = cloudinary.uploader().upload(fileUpload, params);

    cleanDisk(fileUpload);

    return (String) uploadResult.get("url");
  }

  @Override
  public String uploadVideo(MultipartFile file) throws IOException {
    String publicValue = generatePublicValue(file.getOriginalFilename());
    File fileUpload = convert(file);
    Map<String, Object> params = new HashMap<>();

    params.put("resource_type", "video");
    params.put("public_id", publicValue);

    Map<?, ?> uploadResult = cloudinary.uploader().upload(fileUpload, params);

    cleanDisk(fileUpload);

    return (String) uploadResult.get("url");
  }

  @Override
  public void cleanDisk(File file) {
    try {
      log.info("file.toPath is {}", file.toPath());
      Path filePath = file.toPath();
      Files.delete(filePath);
    } catch (IOException e) {
      log.error("Error");
    }
  }

  @Override
  public File convert(MultipartFile file) throws IOException {

    String originalFilename =
        Optional.ofNullable(file)
            .map(MultipartFile::getOriginalFilename)
            .orElseThrow(() -> new AppException("File is empty"));

    File convFile =
        new File(
            StringUtils.join(
                generatePublicValue(file.getOriginalFilename()), getFileName(originalFilename)));

    try (InputStream is = file.getInputStream()) {
      Files.copy(is, convFile.toPath());
    }

    return convFile;
  }

  public String generatePublicValue(String originalName) {
    String fileName = getFileName(originalName)[0];

    return StringUtils.join(UUID.randomUUID().toString(), "_", fileName);
  }

  public String[] getFileName(String originalName) {
    return originalName.split("\\.");
  }
}
