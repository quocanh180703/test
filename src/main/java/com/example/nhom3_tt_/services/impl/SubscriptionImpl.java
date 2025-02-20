package com.example.nhom3_tt_.services.impl;

import com.example.nhom3_tt_.dtos.requests.subscription.SubscriptionCreateRequest;
import com.example.nhom3_tt_.dtos.requests.subscription.SubscriptionDeleteRequest;
import com.example.nhom3_tt_.dtos.response.PageResponse;
import com.example.nhom3_tt_.dtos.response.instructor.InstructorDetailInfo;
import com.example.nhom3_tt_.dtos.response.instructor.StudentDetailInfo;
import com.example.nhom3_tt_.exception.AppException;
import com.example.nhom3_tt_.mappers.SubscriptionMapper;
import com.example.nhom3_tt_.models.Subscription;
import com.example.nhom3_tt_.models.User;
import com.example.nhom3_tt_.repositories.SubscriptionRepository;
import com.example.nhom3_tt_.repositories.UserRepository;
import com.example.nhom3_tt_.services.SubscriptionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import static com.example.nhom3_tt_.exception.ErrorCode.INSTRUCTOR_NOT_FOUND;
import static com.example.nhom3_tt_.exception.ErrorCode.USER_NOT_FOUND;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscriptionImpl implements SubscriptionService {

  private final SubscriptionRepository subscriptionRepository;

  private final SubscriptionMapper subscriptionMapper;

  private final UserRepository userRepository;

  private static String getUserName() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return authentication.getName();
  }

  @Override
  public String createSubscription(SubscriptionCreateRequest request) {
    String userName = getUserName();

    User instructor =
        userRepository
            .findById(request.getInstructor_id())
            .orElseThrow(() -> new AppException(INSTRUCTOR_NOT_FOUND));

    User student =
        userRepository.findByUsername(userName).orElseThrow(() -> new AppException(USER_NOT_FOUND));

    Subscription subscription = subscriptionMapper.toSubscription(request);
    subscription.setStudent(student);
    subscription.setInstructor(instructor);

    subscriptionRepository.save(subscription);

    return "Subscribe successful";
  }

  @Override
  @Transactional
  public void deleteSubscription(SubscriptionDeleteRequest request) {
    String userName = getUserName();

    User instructor =
        userRepository
            .findById(request.getInstructor_id())
            .orElseThrow(() -> new AppException(INSTRUCTOR_NOT_FOUND));

    User student =
        userRepository
            .findByUsername(userName)
            .orElseThrow(() -> new AppException(USER_NOT_FOUND));

    subscriptionRepository.deleteByStudentAndInstructor(student, instructor);
  }

  @Override
  public PageResponse<List<InstructorDetailInfo>> getAllSubscriptions(int pageNo, int pageSize) {
    String userName = getUserName();

    User user =
        userRepository.findByUsername(userName).orElseThrow(() -> new AppException(USER_NOT_FOUND));

    if (pageNo > 0) {
      pageNo = pageNo - 1;
    }

    Pageable pageable = PageRequest.of(pageNo, pageSize);
    Page<InstructorDetailInfo> subscriptions =
        subscriptionRepository.findAllFollowedUsersByUserId(user.getId(), pageable);

    List<InstructorDetailInfo> response =
        subscriptions.stream()
            .map(
                instructor ->
                    InstructorDetailInfo.builder()
                        .id(instructor.getId())
                        .fullName(instructor.getFullName())
                        .email(instructor.getEmail())
                        .build())
            .toList();

    return PageResponse.<List<InstructorDetailInfo>>builder()
        .pageNO(pageNo)
        .pageSize(pageSize)
        .totalPage(subscriptions.getTotalPages())
        .items(response)
        .build();
  }

  @Override
  public PageResponse<List<StudentDetailInfo>> getAllSubscriber(int pageNo, int pageSize) {
    String userName = getUserName();

    User user =
        userRepository.findByUsername(userName).orElseThrow(() -> new AppException(USER_NOT_FOUND));

    if (pageNo > 0) {
      pageNo = pageNo - 1;
    }

    Pageable pageable = PageRequest.of(pageNo, pageSize);
    Page<StudentDetailInfo> subscriptions =
        subscriptionRepository.findAllFollowedUsersByInstructorId(user.getId(), pageable);

    List<StudentDetailInfo> response =
        subscriptions.stream()
            .map(
                instructor ->
                    StudentDetailInfo.builder()
                        .id(instructor.getId())
                        .fullName(instructor.getFullName())
                        .email(instructor.getEmail())
                        .build())
            .toList();

    return PageResponse.<List<StudentDetailInfo>>builder()
        .pageNO(pageNo)
        .pageSize(pageSize)
        .totalPage(subscriptions.getTotalPages())
        .items(response)
        .build();
  }
}
