package com.example.nhom3_tt_.dtos.response.profile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EditProfileResponse {

  private String fullname;

  private String email;

  private String phone;

  private String avatar;

  private String position;

  private String description;
}
