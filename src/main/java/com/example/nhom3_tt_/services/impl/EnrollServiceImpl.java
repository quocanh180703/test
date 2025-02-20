package com.example.nhom3_tt_.services.impl;

import com.example.nhom3_tt_.dtos.requests.EnrollRequest;
import com.example.nhom3_tt_.dtos.response.EnrollResponse;
import com.example.nhom3_tt_.dtos.response.PageResponse;
import com.example.nhom3_tt_.exception.AppException;
import com.example.nhom3_tt_.exception.ErrorCode;
import com.example.nhom3_tt_.mappers.EnrollMapper;
import com.example.nhom3_tt_.models.Course;
import com.example.nhom3_tt_.models.Enroll;
import com.example.nhom3_tt_.models.User;
import com.example.nhom3_tt_.repositories.CourseRepository;
import com.example.nhom3_tt_.repositories.EnrollRepository;
import com.example.nhom3_tt_.repositories.UserRepository;
import com.example.nhom3_tt_.services.EnrollService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class EnrollServiceImpl implements EnrollService {
  private final EnrollRepository enrollRepository;
  private final EnrollMapper enrollMapper;
  private final CourseRepository courseRepository;
  private final UserRepository userRepository;

  @Override
  public Enroll enroll(EnrollRequest req) {
    enrollRepository
        .findByStudentIdAndCourseId(req.getStudentId(), req.getCourseId())
        .ifPresent(
            enroll -> {
              log.info(
                  "Student {} already enrolled in course {}",
                  req.getStudentId(),
                  req.getCourseId());
              throw new AppException(ErrorCode.STUDENT_ALREADY_ENROLLED);
            });
    Course course =
        courseRepository
            .findById(req.getCourseId())
            .orElseThrow(
                () -> {
                  log.info("Course with id {} not found", req.getCourseId());
                  return new AppException(ErrorCode.COURSE_NOT_FOUND);
                });
    User user =
        userRepository
            .findById(req.getStudentId())
            .orElseThrow(
                () -> {
                  log.info("Student with id {} not found", req.getStudentId());
                  return new AppException(ErrorCode.USER_NOT_FOUND);
                });
    Enroll enroll = Enroll.builder().course(course).student(user).build();
    return enrollRepository.save(enroll);
  }

  @Override
  public EnrollResponse findById(Long id) {
    ;
    return enrollMapper.convertToResponse(
        enrollRepository
            .findById(id)
            .orElseThrow(
                () -> {
                  log.info("Enroll with id {} not found", id);
                  return new AppException(ErrorCode.ENROLL_NOT_FOUND);
                }));
  }

  @Override
  public PageResponse<?> findAll(Pageable pageable) {
    Page<Enroll> enrollPage = enrollRepository.findAll(pageable);
    List<EnrollResponse> res = enrollPage.stream().map(enrollMapper::convertToResponse).toList();
    return PageResponse.builder()
        .items(res)
        .pageNO(pageable.getPageNumber())
        .pageSize(pageable.getPageSize())
        .totalPage(enrollPage.getTotalPages())
        .build();
  }

  @Override
  public PageResponse<?> findAllByStudentId(Long studentId, Pageable pageable) {
    Page<Enroll> enrollPage = enrollRepository.findAllByStudentId(studentId, pageable);
    List<EnrollResponse> res = enrollPage.stream().map(enrollMapper::convertToResponse).toList();
    return PageResponse.builder()
        .items(res)
        .pageNO(pageable.getPageNumber())
        .pageSize(pageable.getPageSize())
        .totalPage(enrollPage.getTotalPages())
        .build();
  }

  @Override
  public PageResponse<?> findAllByCourseId(Long courseId, Pageable pageable) {
    Page<Enroll> enrollPage = enrollRepository.findAllByCourseId(courseId, pageable);
    List<EnrollResponse> res = enrollPage.stream().map(enrollMapper::convertToResponse).toList();
    return PageResponse.builder()
        .items(res)
        .pageNO(pageable.getPageNumber())
        .pageSize(pageable.getPageSize())
        .totalPage(enrollPage.getTotalPages())
        .build();
  }

  @Override
  public void deleteEnrollById(Long id) {
    Enroll enroll =
        enrollRepository
            .findById(id)
            .orElseThrow(
                () -> {
                  log.info("Enroll with id {} not found", id);
                  return new AppException(ErrorCode.ENROLL_NOT_FOUND);
                });
    enrollRepository.delete(enroll);
  }

  @Override
  public void deleteEnroll(Long studentId, Long courseId) {
    Optional<Enroll> enroll = enrollRepository.findByStudentIdAndCourseId(studentId, courseId);

    if (enroll.isEmpty()) {
      log.info("Student {} not enrolled in course {}", studentId, courseId);
      throw new AppException(ErrorCode.STUDENT_NOT_ENROLLED);
    } else {
      enrollRepository.delete(enroll.get());
    }
  }

  @Override
  public Boolean isEnrolled(Long studentId, Long courseId) {
    Enroll enroll = enrollRepository.findByStudentIdAndCourseId(studentId, courseId).orElse(null);
    return enroll != null;
  }

  public Page<EnrollResponse> convertToEnrollResponsePage(Page<Enroll> enrollPage) {
    return new PageImpl<>(
        enrollPage.getContent().stream()
            .map(enrollMapper::convertToResponse)
            .collect(Collectors.toList()),
        enrollPage.getPageable(),
        enrollPage.getTotalElements());
  }
}
