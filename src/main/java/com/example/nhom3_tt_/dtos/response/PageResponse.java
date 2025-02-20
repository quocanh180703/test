package com.example.nhom3_tt_.dtos.response;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageResponse<T> implements Serializable {
  private int pageNO;
  private int pageSize;
  private long totalPage;
  private T items;
}
