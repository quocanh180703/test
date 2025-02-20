package com.example.nhom3_tt_.mappers;

import com.example.nhom3_tt_.dtos.requests.subscription.SubscriptionCreateRequest;
import com.example.nhom3_tt_.models.Subscription;
import org.mapstruct.Mapper;

@Mapper
public interface SubscriptionMapper {

  Subscription toSubscription(SubscriptionCreateRequest request);
}
