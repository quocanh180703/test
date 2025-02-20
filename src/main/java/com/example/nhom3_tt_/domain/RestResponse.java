package com.example.nhom3_tt_.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RestResponse<T> {

  public int statusCode;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  public String error;

  // String or ArrayList
  @JsonInclude(JsonInclude.Include.NON_NULL)
  public Object message;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  public T data;
}
