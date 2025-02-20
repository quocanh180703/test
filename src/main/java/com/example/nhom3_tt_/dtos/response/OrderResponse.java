package com.example.nhom3_tt_.dtos.response;

import com.example.nhom3_tt_.dtos.User.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponse {
  private Long id;
  private UserDTO student;
  private Date orderDate;
  private Double totalAmount;
  private List<Long> orderDetailIds;

  public OrderResponse(Long id) {
    this.id = id;
  }
}
