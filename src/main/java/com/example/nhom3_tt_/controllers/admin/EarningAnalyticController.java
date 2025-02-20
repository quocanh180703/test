package com.example.nhom3_tt_.controllers.admin;

import com.example.nhom3_tt_.dtos.requests.LoginRequest;
import com.example.nhom3_tt_.dtos.requests.profile.EditProfileRequest;
import com.example.nhom3_tt_.dtos.response.earningAnalytic.EarningAnalyticResponse;
import com.example.nhom3_tt_.dtos.response.profile.EditProfileResponse;
import com.example.nhom3_tt_.models.Order;
import com.example.nhom3_tt_.services.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/earning-analytics")
@RequiredArgsConstructor
public class EarningAnalyticController {
    private final OrderService orderService;
    @GetMapping()
    public ResponseEntity<?> getEarning() {
        List<EarningAnalyticResponse> listOrder = orderService.getEarning();
        return ResponseEntity.ok().body(listOrder);
    }

    @GetMapping("/total")
    public ResponseEntity<?> getEarningTotal() {
        Double total = orderService.getEarningTotal();
        return ResponseEntity.ok().body(total);
    }

    // Lấy tổng thu nhập theo ngày
    @GetMapping("/daily")
    public ResponseEntity<?> getEarningByDay(@RequestParam String date) {
        Double dailyTotal = orderService.getEarningByDay(date);
        return ResponseEntity.ok().body(dailyTotal);
    }

//    // Lấy tổng thu nhập theo tháng
    @GetMapping("/monthly")
    public ResponseEntity<?> getEarningByMonth(@RequestParam String month) {
        // month ở đây là có định dangn là YYYY-MM
        Double monthlyTotal = orderService.getEarningByMonth(month);
        return ResponseEntity.ok().body(monthlyTotal);
    }
//
//    // Lấy tổng thu nhập theo năm
    @GetMapping("/yearly")
    public ResponseEntity<?> getEarningByYear(@RequestParam String year) {
        Double yearlyTotal = orderService.getEarningByYear(year);
        return ResponseEntity.ok().body(yearlyTotal);
    }


}
