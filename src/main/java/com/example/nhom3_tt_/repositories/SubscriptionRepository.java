package com.example.nhom3_tt_.repositories;

import com.example.nhom3_tt_.dtos.response.instructor.InstructorDetailInfo;
import com.example.nhom3_tt_.dtos.response.instructor.StudentDetailInfo;
import com.example.nhom3_tt_.models.Subscription;
import com.example.nhom3_tt_.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

  void deleteByStudentAndInstructor(User student, User instructor);

  @Query(
      "SELECT new com.example.nhom3_tt_.dtos.response.instructor.InstructorDetailInfo(i.id, i.fullname, i.email) "
          + "FROM Subscription s JOIN s.instructor i "
          + "WHERE s.student.id = :userId")
  Page<InstructorDetailInfo> findAllFollowedUsersByUserId(
      @Param("userId") Long userId, Pageable pageable);

  @Query(
      "SELECT new com.example.nhom3_tt_.dtos.response.instructor.StudentDetailInfo(i.id, i.fullname, i.email) "
          + "FROM Subscription s JOIN s.instructor i "
          + "WHERE s.instructor.id = :userId")
  Page<StudentDetailInfo> findAllFollowedUsersByInstructorId(
      @Param("userId") Long userId, Pageable pageable);
}
