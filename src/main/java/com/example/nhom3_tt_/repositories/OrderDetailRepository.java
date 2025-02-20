package com.example.nhom3_tt_.repositories;

import com.example.nhom3_tt_.models.OrderDetail;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {

  List<OrderDetail> findByOrderStudentId(Long studentId);

  List<OrderDetail> findByOrderId(Long orderId);
}
