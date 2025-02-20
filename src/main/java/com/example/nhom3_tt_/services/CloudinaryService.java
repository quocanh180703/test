package com.example.nhom3_tt_.services;

import java.io.File;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.example.nhom3_tt_.dtos.requests.lecture.LectureVideoFolderStructure;

public interface CloudinaryService {

  String uploadVideo(MultipartFile file, LectureVideoFolderStructure request) throws IOException;

  String uploadImage(MultipartFile file, LectureVideoFolderStructure request) throws IOException;

  String uploadImage(MultipartFile file) throws IOException;

  String uploadVideo(MultipartFile file) throws IOException;

  Object convert(MultipartFile multipartFile) throws IOException;

  void cleanDisk(File file);
}
