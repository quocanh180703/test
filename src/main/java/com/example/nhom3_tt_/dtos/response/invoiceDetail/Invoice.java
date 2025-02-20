package com.example.nhom3_tt_.dtos.response.invoiceDetail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Invoice {
  private String id;
  private String date;
  private String notice;
  private String total;
  private Subject from;
  private Subject to;
  private List<InvoiceItem> items;
}
