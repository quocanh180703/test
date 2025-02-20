package com.example.nhom3_tt_.repositories;

import com.example.nhom3_tt_.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByUsername(String username);

  Optional<User> findByEmail(String email);

  Optional<User> findById(Long id);

  Boolean existsByEmail(String email);
}
