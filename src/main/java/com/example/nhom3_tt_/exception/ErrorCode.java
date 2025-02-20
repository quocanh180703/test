package com.example.nhom3_tt_.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
  UNCATEGORIZED_EXCEPTION(500, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
  WRONG_PASSWORD(400, "Wrong password", HttpStatus.BAD_REQUEST),
  PASSWORD_NOT_EQUAL(400, "New password and Confirm password must equal", HttpStatus.BAD_REQUEST),
  USER_NOT_EXIST(400, "User not existed", HttpStatus.BAD_REQUEST),
  EMAIL_NOT_EXIST(400, "Email not existed", HttpStatus.BAD_REQUEST),
  USER_NAME_EXISTED(400, "USERNAME EXISTED", HttpStatus.BAD_REQUEST),
  OTP_NOT_SUCCESS(400, "OTP is not correct ", HttpStatus.BAD_REQUEST),
  OTP_IS_EXPIRY(400, "OTP is expiry ", HttpStatus.BAD_REQUEST),
  USER_NOT_FOUND(404, "User not found", HttpStatus.BAD_REQUEST),
  ORDER_NOT_FOUND(404, "Order not found", HttpStatus.BAD_REQUEST),
  ORDER_DETAIL_NOT_FOUND(404, "Order detail not found", HttpStatus.BAD_REQUEST),
  INSTRUCTOR_NOT_FOUND(404, "Instructor not found", HttpStatus.BAD_REQUEST),
  EMAIL_EXISTED(400, "Email existed", HttpStatus.BAD_REQUEST),
  STUDENT_ALREADY_ENROLLED(400, "Student already enrolled", HttpStatus.BAD_REQUEST),
  COURSE_NOT_FOUND(404, "Course not found", HttpStatus.BAD_REQUEST),
  COURSE_ALREADY_ENROLLED(400, "Course already enrolled", HttpStatus.BAD_REQUEST),
  ENROLL_NOT_FOUND(400, "Enroll not found", HttpStatus.BAD_REQUEST),
  STUDENT_NOT_ENROLLED(400, "Student not enrolled", HttpStatus.BAD_REQUEST),
  COURSE_PRICE_NOT_ACCEPTED(
      400, "Discount price cannot be greater than Regular price!", HttpStatus.BAD_REQUEST),
  CART_ITEM_EMPTY(400, "Cart item empty", HttpStatus.BAD_REQUEST),
  INVALID_PRODUCT_ID_FORMAT(400, "List product must be in a valid format", HttpStatus.BAD_REQUEST),
  INVALID_PRICE(400, "Invalid price", HttpStatus.BAD_REQUEST),
  CART_NOT_FOUND(404, "Cart not found", HttpStatus.NOT_FOUND),
  CART_ITEM_ALREADY_EXIST(409, "Cart item is already exists", HttpStatus.CONFLICT),
  CART_ITEM_NOT_FOUND(404, "Cart item not found", HttpStatus.NOT_FOUND),
  DUPLICATE_REVIEW(409, "You can only add 1 review in 1 course!", HttpStatus.CONFLICT),
  REVIEW_NOT_FOUND(404, "Review not found", HttpStatus.NOT_FOUND),
  ACCESS_DENIED(
      403,
      "You are not allowed to modify or delete a record that you do not own.",
      HttpStatus.FORBIDDEN),
  QUIZ_NOT_FOUND(404, "Quiz not found", HttpStatus.NOT_FOUND),
  FILE_NOT_EMPTY(
      400, "File is missing or empty. Please upload a valid file.", HttpStatus.FORBIDDEN),
  NOT_INSTRUCTOR(403, "You are not the instructor of this course", HttpStatus.FORBIDDEN),
  ;

  private int code;
  private String message;
  private HttpStatusCode statusCode;

  ErrorCode(int code, String message, HttpStatusCode statusCode) {
    this.code = code;
    this.message = message;
    this.statusCode = statusCode;
  }
}
