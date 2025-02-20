package com.example.nhom3_tt_.services;

import com.example.nhom3_tt_.dtos.requests.subscription.SubscriptionCreateRequest;
import com.example.nhom3_tt_.dtos.requests.subscription.SubscriptionDeleteRequest;
import com.example.nhom3_tt_.dtos.response.PageResponse;
import com.example.nhom3_tt_.dtos.response.instructor.InstructorDetailInfo;
import com.example.nhom3_tt_.dtos.response.instructor.StudentDetailInfo;

import java.util.List;

public interface SubscriptionService {

  String createSubscription(SubscriptionCreateRequest request);

  void deleteSubscription(SubscriptionDeleteRequest request);

  PageResponse<List<InstructorDetailInfo>> getAllSubscriptions(int pageNo, int pageSize);

  PageResponse<List<StudentDetailInfo>> getAllSubscriber(int pageNo, int pageSize);
}
