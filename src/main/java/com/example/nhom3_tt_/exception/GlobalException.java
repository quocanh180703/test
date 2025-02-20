package com.example.nhom3_tt_.exception;

import com.example.nhom3_tt_.domain.RestResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalException {

  @ExceptionHandler(value = {Exception.class})
  public ResponseEntity<RestResponse<Object>> handleException(Exception exception) {
    RestResponse<Object> res = new RestResponse<>();

    res.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
    res.setMessage(
        exception.getMessage() != null
            ? exception.getMessage()
            : "An unexpected error occurred, check ExceptionName");
    res.setError(exception.getClass().getSimpleName());
    //    exception.printStackTrace();
    return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(value = AppException.class)
  public ResponseEntity<RestResponse<Object>> handleIdException(AppException exception) {
    RestResponse<Object> res = new RestResponse<>();
    //    res.setStatusCode(HttpStatus.BAD_REQUEST.value());
    res.setMessage(exception.getMessage());
    res.setStatusCode(exception.getErrorCode().getCode());
    res.setError("App Exception");
    //    exception.printStackTrace();
    return ResponseEntity.status(exception.getErrorCode().getCode()).body(res);
  }

  @ExceptionHandler(value = MethodArgumentNotValidException.class)
  public ResponseEntity<RestResponse<Object>> handleValidationException(
      MethodArgumentNotValidException exception) {
    // Lấy tất cả lỗi từ BindingResult
    //        Map<String, List<String>> errors = exception.getBindingResult()
    //                .getFieldErrors()
    //                .stream()
    //                .collect(Collectors.groupingBy(
    //                        FieldError::getField,
    //                        Collectors.mapping(
    //                                fieldError ->
    // Optional.ofNullable(fieldError.getDefaultMessage()).orElse("Default Message"),
    //                                Collectors.toList()
    //                        )
    //                ));

    String error = exception.getBindingResult().getFieldErrors().getFirst().getDefaultMessage();

    RestResponse<Object> res = new RestResponse<>();
    res.setStatusCode(HttpStatus.BAD_REQUEST.value());
    res.setMessage(error);
    res.setError("Validate failed!");

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
  }

  @ExceptionHandler(value = DataIntegrityViolationException.class)
  public ResponseEntity<RestResponse<Object>> handleIdDataIntegrityViolation(
      DataIntegrityViolationException exception) {
    RestResponse<Object> res = new RestResponse<>();
    String message = exception.getMessage();

    log.info(exception.getMessage());
    log.info(exception.getLocalizedMessage());
    //    exception.printStackTrace();

    res.setMessage(message);

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
  }

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<?> handleNotFoundException(NotFoundException ne) {
    log.error("Error: {}", ne.getMessage());
    RestResponse<Object> response =
        RestResponse.<Object>builder()
            .statusCode(HttpStatus.NOT_FOUND.value())
            .error("Not Found")
            .message(ne.getMessage())
            .build();
    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<?> handleAccessDeniedException(AccessDeniedException ne) {
    log.error("Error: {}", ne.getMessage());
    RestResponse<Object> response =
        RestResponse.builder()
            .statusCode(HttpStatus.FORBIDDEN.value())
            .error("Access Denied")
            .message("You don't have permission to do this action")
            .build();

    return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
  }

  @ExceptionHandler(CustomException.class)
  public ResponseEntity<?> handleCustomException(CustomException customException) {
    HttpStatus httpStatus = HttpStatus.resolve(customException.getStatusCode());
    String errorName = (httpStatus != null) ? httpStatus.name() : "UNKNOWN_STATUS";
    customException.printStackTrace();

    RestResponse<Object> response =
        RestResponse.<Object>builder()
            .statusCode(customException.getStatusCode())
            .error(errorName)
            .message(customException.getMessage())
            .build();
    return ResponseEntity.status(response.getStatusCode()).body(response);
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException ex) {
    log.error("Error: {}", ex.getMessage());
    //    ex.printStackTrace();

    RestResponse<Object> response =
        RestResponse.<Object>builder()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .error("Invalid input")
            .message(ex.getMessage())
            .build();
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  // input sai kiểu dữ liệu (giữ nguyên lỗi, chỉ đổi code sang 400)
  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<RestResponse<Object>> handleHttpMessageNotReadableException(
      HttpMessageNotReadableException exception) {

    RestResponse<Object> res = new RestResponse<>();
    String message = exception.getMessage();
    String error = exception.getClass().getSimpleName();

    res.setStatusCode(HttpStatus.BAD_REQUEST.value());
    res.setMessage(message);
    res.setError(error);
    //    exception.printStackTrace();
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
  }
}
