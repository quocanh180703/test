package com.example.nhom3_tt_.util;

import com.example.nhom3_tt_.dtos.response.invoiceDetail.Invoice;
import com.example.nhom3_tt_.dtos.response.invoiceDetail.Subject;
import com.example.nhom3_tt_.exception.AppException;
import com.example.nhom3_tt_.exception.ErrorCode;
import com.example.nhom3_tt_.models.User;
import com.example.nhom3_tt_.repositories.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.io.UnsupportedEncodingException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailService {

  private static final String ADMIN = "admin";
  private static final String OTP_MESSAGE = "OTP is ";
  private static final long OTP_EXPIRY = 300;
  private final JavaMailSender mailSender;
  private final SpringTemplateEngine templateEngine;
  private final UserRepository userRepository;

  @Value("${spring.mail.from}")
  private String emailFrom;

  @Value("${spring.application.serverName}")
  private String serverName;

  private static String generateOtp() {
    Random random = new Random();
    StringBuilder otp = new StringBuilder();

    for (int i = 0; i < 6; i++) {
      int digit = 1 + random.nextInt(9);
      otp.append(digit);
    }

    return otp.toString();
  }

  @Async
  public CompletableFuture<String> sendEmail(User user, String email)
      throws MessagingException, UnsupportedEncodingException {

    //    User user =
    //        userRepository
    //            .findByEmail(email)
    //            .orElseThrow(() -> new AppException(ErrorCode.EMAIL_NOT_EXIST));

    MimeMessage message = mailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
    Context context = new Context();
    StringBuilder content = new StringBuilder(OTP_MESSAGE);
    String otp = generateOtp();
    content.append(otp);

    Map<String, Object> properties = new HashMap<>();
    properties.put("name", user.getFullname());
    properties.put("message", content);
    properties.put("subject", ADMIN);
    context.setVariables(properties);

    helper.setFrom(emailFrom, "ADMIN");
    helper.setTo(email);
    helper.setSubject("Reset Password");
    String html = templateEngine.process("confirm-email.html", context);
    helper.setText(html, true);

    mailSender.send(message);

    user.setOTP(otp);
    user.setOtpExpirationDate(
        new Date(Instant.now().plus(OTP_EXPIRY, ChronoUnit.SECONDS).toEpochMilli()));

    userRepository.save(user);

    return CompletableFuture.completedFuture("Sent");
  }

  @Async
  public CompletableFuture<String> sendInvoice(Invoice invoice)
      throws MessagingException, UnsupportedEncodingException {

    invoice.setFrom(Subject.builder().name(ADMIN).email(emailFrom).website("Team 3 LMS").build());

    MimeMessage message = mailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
    Context context = new Context();

    Map<String, Object> properties = new HashMap<>();
    properties.put("invoice", invoice);
    context.setVariables(properties);
    helper.setFrom(emailFrom, "ADMIN");
    helper.setTo(invoice.getTo().getEmail());
    helper.setSubject("Invoice");
    String html = templateEngine.process("payment-successful.html", context);
    helper.setText(html, true);

    mailSender.send(message);

    return CompletableFuture.completedFuture("Sent");
  }
}
