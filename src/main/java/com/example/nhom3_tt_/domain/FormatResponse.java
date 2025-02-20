package com.example.nhom3_tt_.domain;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice(basePackages = {"com.example.nhom3_tt_.controllers"})
public class FormatResponse implements ResponseBodyAdvice<Object> {

  @Override
  public boolean supports(
      MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
    return true;
  }

  @Override
  public Object beforeBodyWrite(
      Object body,
      MethodParameter returnType,
      MediaType selectedContentType,
      Class<? extends HttpMessageConverter<?>> selectedConverterType,
      ServerHttpRequest request,
      ServerHttpResponse response) {
    HttpServletResponse servletResponse =
        ((ServletServerHttpResponse) response).getServletResponse();
    int status = servletResponse.getStatus();

    RestResponse<Object> res = new RestResponse<>();
    res.setStatusCode(status);

    if (body instanceof String) {
      return body;
    }

    if (status >= 400) {
      // case error
      return body;

    } else {
      // case success
      res.setData(body);
      res.setMessage("CALL API SUCCESS!");
    }

    return res;
  }
}
