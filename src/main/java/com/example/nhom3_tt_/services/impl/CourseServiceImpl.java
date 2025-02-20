package com.example.nhom3_tt_.services.impl;

import com.example.nhom3_tt_.dtos.requests.CourseRequest;
import com.example.nhom3_tt_.dtos.response.CourseResponse;
import com.example.nhom3_tt_.exception.AppException;
import com.example.nhom3_tt_.exception.CustomException;
import com.example.nhom3_tt_.exception.ErrorCode;
import com.example.nhom3_tt_.exception.NotFoundException;
import com.example.nhom3_tt_.mappers.CourseMapper;
import com.example.nhom3_tt_.models.*;
import com.example.nhom3_tt_.repositories.*;
import com.example.nhom3_tt_.services.CloudinaryService;
import com.example.nhom3_tt_.services.CourseService;
import jakarta.transaction.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

  private final CourseRepository courseRepository;
  private final CourseMapper courseMapper;
  private final UserRepository userRepository;
  private final CategoryRepository categoryRepository;
  private final CloudinaryService cloudinaryService;

  @Transactional
  @Override
  public CourseResponse create(CourseRequest courseRequest) {
    Long instructorId = courseRequest.getInstructorId();
    User instructor =
        userRepository
            .findById(instructorId)
            .orElseThrow(
                () -> new NotFoundException("Instructor not found with id=" + instructorId));
    Long categoryId = courseRequest.getCategoryId();
    Category category =
        categoryRepository
            .findById(categoryId)
            .orElseThrow(() -> new NotFoundException("Category not found with id=" + categoryId));

    Course course = courseMapper.convertToEntity(courseRequest);
    course.setInstructor(instructor);
    course.setCategory(category);
    course.setStatus(ECourseStatus.PENDING);

    course = courseRepository.save(course);

    return courseMapper.convertToResponse(course);
  }

  @Override
  public List<CourseResponse> getAll(Pageable pageable) {
    return courseRepository.findAll(pageable).stream()
        .map(courseMapper::convertToResponse)
        .toList();
  }

  @Override
  public List<CourseResponse> getAllApproved(Pageable pageable) {
    return courseRepository.findByStatus(ECourseStatus.APPROVED, pageable).stream()
        .map(courseMapper::convertToResponse)
        .toList();
  }

  @Override
  public List<CourseResponse> getAllPending(Pageable pageable) {
    return courseRepository.findByStatus(ECourseStatus.PENDING, pageable).stream()
        .map(courseMapper::convertToResponse)
        .toList();
  }

  @Override
  public List<CourseResponse> getAllReject(Pageable pageable) {
    return courseRepository.findByStatus(ECourseStatus.REJECT, pageable).stream()
        .map(courseMapper::convertToResponse)
        .toList();
  }

  @Override
  public List<CourseResponse> getAll() {
    return courseRepository.findAll().stream().map(courseMapper::convertToResponse).toList();
  }

  @Override
  public CourseResponse getById(Long id) {
    Course course =
        courseRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("Course is not found"));
    return courseMapper.convertToResponse(course);
  }

  @Override
  public Course getByIdEntity(Long id) {
    return courseRepository
        .findById(id)
        .orElseThrow(() -> new NotFoundException("Course is not found"));
  }

  @Transactional
  @Override
  public CourseResponse update(Long id, CourseRequest newCourse) {
    if (!Objects.equals(
        courseRepository.findById(id).get().getInstructor().getId(),
        ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId())) {
      throw new AppException(ErrorCode.NOT_INSTRUCTOR);
    }

    Course course =
        courseRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("Course is not found"));

    Long iId = newCourse.getInstructorId();
    User instructor =
        userRepository
            .findById(iId)
            .orElseThrow(() -> new NotFoundException("Instructor cannot found with id = " + iId));
    Long cId = newCourse.getCategoryId();
    Category category =
        categoryRepository
            .findById(cId)
            .orElseThrow(() -> new NotFoundException("Category cannot found with id = " + cId));

    courseMapper.updateCourseFromRequest(newCourse, course);
    course.setInstructor(instructor);
    course.setCategory(category);
    Course updatedCourse = courseRepository.save(course);
    return courseMapper.convertToResponse(updatedCourse);
  }

  @Override
  public CourseResponse forceDelete(Long id) {
    if (!Objects.equals(
        courseRepository.findById(id).get().getInstructor().getId(),
        ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId())) {
      throw new AppException(ErrorCode.NOT_INSTRUCTOR);
    }

    Course course =
        courseRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("Course is not found"));
    CourseResponse response = courseMapper.convertToResponse(course);
    courseRepository.deleteById(id);
    return response;
  }

  @Override
  public List<Course> getCoursesByCategory(Category category) {

    return courseRepository.findByCategory(category);
  }

  @Override
  public List<CourseResponse> getCoursesByLevel(String level, Pageable pageable) {
    try {
      ETypeLevel eTypeLevel = ETypeLevel.valueOf(level.toUpperCase());
      Page<Course> courses = courseRepository.findByLevel(eTypeLevel, pageable);
      return courses.stream().map(courseMapper::convertToResponse).toList();

    } catch (IllegalArgumentException ex) {
      throw new NotFoundException("Invalid level: '" + level);
    }
  }

  @Override
  public List<CourseResponse> getCoursesByCategoryId(Long categoryId, Pageable pageable) {
    Page<Course> courses = courseRepository.findByCategoryId(categoryId, pageable);
    return courses.stream().map(courseMapper::convertToResponse).toList();
  }

  @Override
  public List<CourseResponse> getCourseByIntructorId(Long instructorId, Pageable pageable) {
    Page<Course> courses = courseRepository.findByInstructorId(instructorId, pageable);
    return courses.stream().map(courseMapper::convertToResponse).toList();
  }

  @Override
  public List<CourseResponse> searchByTitle(String title, Pageable pageable) {
    String input = title.trim();
    Page<Course> courses = courseRepository.findByTitleContainingIgnoreCase(input, pageable);
    return courses.stream().map(courseMapper::convertToResponse).toList();
  }

  @Transactional
  @Override
  public CourseResponse approveCourse(Long id) {
    Course course =
        courseRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("No course ID found: " + id));

    ECourseStatus currentStatus = course.getStatus();

    if (currentStatus == ECourseStatus.APPROVED) {
      throw new CustomException(
          "The course has been previously approved", HttpStatus.BAD_REQUEST.value());
    }

    course.setStatus(ECourseStatus.APPROVED);
    course = courseRepository.save(course);

    return courseMapper.convertToResponse(course);
  }

  @Transactional
  @Override
  public CourseResponse rejectCourse(Long id) {
    Course course =
        courseRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("No course ID found: " + id));

    ECourseStatus currentStatus = course.getStatus();

    if (currentStatus == ECourseStatus.REJECT) {
      throw new CustomException(
          "The course has been rejected before", HttpStatus.BAD_REQUEST.value());
    }

    course.setStatus(ECourseStatus.REJECT);
    course = courseRepository.save(course);

    return courseMapper.convertToResponse(course);
  }

  @Override
  public CourseResponse uploadThumbnail(Long id, MultipartFile thumbnail) throws IOException {
    if (!Objects.equals(
        courseRepository.findById(id).get().getInstructor().getId(),
        ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId())) {
      throw new AppException(ErrorCode.NOT_INSTRUCTOR);
    }

    Course course =
        courseRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("No course ID found: " + id));

    String thumbnailUrl = cloudinaryService.uploadImage(thumbnail);
    course.setThumbnail(thumbnailUrl);
    course = courseRepository.save(course);

    return courseMapper.convertToResponse(course);
  }

  @Override
  public CourseResponse uploadVideo(Long id, MultipartFile video) throws IOException {
    if (!Objects.equals(
        courseRepository.findById(id).get().getInstructor().getId(),
        ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId())) {
      throw new AppException(ErrorCode.NOT_INSTRUCTOR);
    }

    Course course =
        courseRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("No course ID found: " + id));

    String videoUrl = cloudinaryService.uploadVideo(video);
    course.setIntroVideo(videoUrl);
    course = courseRepository.save(course);

    return courseMapper.convertToResponse(course);
  }
}
