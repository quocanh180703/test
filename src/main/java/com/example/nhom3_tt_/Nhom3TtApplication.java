package com.example.nhom3_tt_;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class Nhom3TtApplication {

  public static void main(String[] args) {
    SpringApplication.run(Nhom3TtApplication.class, args);
  }
}
