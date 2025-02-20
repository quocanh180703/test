package com.example.nhom3_tt_.dtos.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {

  private String username;
  private String fullname;
  private String email;
  private String role;
  private String phone;
  private String avatar;
  private String position;
  private Boolean status;
  private String description;
}
