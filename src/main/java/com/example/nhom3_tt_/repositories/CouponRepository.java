package com.example.nhom3_tt_.repositories;

import com.example.nhom3_tt_.models.Coupon;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {
  Page<Coupon> findAll(Pageable pageable);

  @Query("SELECT c FROM Coupon c WHERE c.expireDay > :now")
  Page<Coupon> findCouponUnexpired(@Param("now") LocalDateTime now, Pageable pageable);

  Optional<Coupon> findByCodeIgnoreCase(String code);
}
