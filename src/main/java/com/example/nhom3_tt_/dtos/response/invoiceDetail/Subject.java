package com.example.nhom3_tt_.dtos.response.invoiceDetail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Subject {
  private String name;
  private String email;
  private String website;
}
