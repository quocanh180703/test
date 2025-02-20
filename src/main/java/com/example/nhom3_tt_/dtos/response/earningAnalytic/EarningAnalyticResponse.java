package com.example.nhom3_tt_.dtos.response.earningAnalytic;

import com.example.nhom3_tt_.models.OrderDetail;
import com.example.nhom3_tt_.models.User;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EarningAnalyticResponse implements Serializable {
  private Long id;
//
//  private User student;
//
//  private List<OrderDetail> orderDetails;

  private Date orderDate;

  private Double totalAmount;

}
