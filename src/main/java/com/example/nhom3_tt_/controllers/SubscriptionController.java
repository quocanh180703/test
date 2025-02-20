package com.example.nhom3_tt_.controllers;

import com.example.nhom3_tt_.domain.RestResponse;
import com.example.nhom3_tt_.dtos.requests.subscription.SubscriptionCreateRequest;
import com.example.nhom3_tt_.dtos.requests.subscription.SubscriptionDeleteRequest;
import com.example.nhom3_tt_.dtos.response.PageResponse;
import com.example.nhom3_tt_.dtos.response.instructor.InstructorDetailInfo;
import com.example.nhom3_tt_.services.SubscriptionService;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {

  private final SubscriptionService subscriptionService;

  @PostMapping
  public ResponseEntity<?> createSubscription(@RequestBody SubscriptionCreateRequest request) {

    ObjectMapper objectMapper = new ObjectMapper();

    return ResponseEntity.status(HttpStatus.CREATED)
        .body(
            objectMapper
                .createObjectNode()
                .put("message", subscriptionService.createSubscription(request)));
  }

  @DeleteMapping
  public ResponseEntity<Void> unSubscribe(@RequestBody SubscriptionDeleteRequest request) {

    subscriptionService.deleteSubscription(request);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/student")
  public ResponseEntity<?> getAllFollowing(
      @RequestParam(defaultValue = "0") int pageNo,
      @RequestParam(defaultValue = "10") int pageSize) {

    var result = subscriptionService.getAllSubscriptions(pageNo, pageSize);
    return ResponseEntity.ok().body(result);
  }

  @GetMapping("/instructor")
  @PreAuthorize("hasAuthority('INSTRUCTOR')")
  public ResponseEntity<?> getAllFollowingByInstructor(
      @RequestParam(defaultValue = "0") int pageNo,
      @RequestParam(defaultValue = "10") int pageSize) {

    var result = subscriptionService.getAllSubscriber(pageNo, pageSize);
    return ResponseEntity.ok().body(result);
  }
}
