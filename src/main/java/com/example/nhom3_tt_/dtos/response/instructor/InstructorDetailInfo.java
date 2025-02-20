package com.example.nhom3_tt_.dtos.response.instructor;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InstructorDetailInfo implements Serializable {

  private Long id;
  private String fullName;
  private String email;
}
