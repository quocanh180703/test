package com.example.nhom3_tt_.repositories;

import com.example.nhom3_tt_.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
  // Nếu bạn cần tìm đơn hàng theo Student ID
  List<Order> findByStudent_Id(Long studentId);

  @Query("SELECT o FROM Order o WHERE FUNCTION('DATE', o.createdAt) = FUNCTION('DATE', :createdAt)")
  List<Order> findAllByCreatedAt(@Param("createdAt") LocalDate createdAt);

  @Query("SELECT o FROM Order o WHERE to_char(o.createdAt, 'YYYY-MM') = :yearMonth")
  List<Order> findAllByCreatedAtMonth(@Param("yearMonth") String yearMonth);

  @Query("SELECT o FROM Order o WHERE to_char(o.createdAt, 'YYYY') = :year")
  List<Order> findAllByCreatedAtYear(@Param("year") String year);

}
