package com.example.nhom3_tt_.service;

import com.example.nhom3_tt_.dtos.response.invoiceDetail.Invoice;
import com.example.nhom3_tt_.dtos.response.invoiceDetail.Subject;
import com.example.nhom3_tt_.exception.AppException;
import com.example.nhom3_tt_.exception.ErrorCode;
import com.example.nhom3_tt_.models.User;
import com.example.nhom3_tt_.repositories.UserRepository;
import com.example.nhom3_tt_.util.MailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.util.ReflectionTestUtils;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static org.hamcrest.Matchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MailServiceTest {
  @Mock private JavaMailSender mailSender;

  @Mock private SpringTemplateEngine templateEngine;

  @Mock private UserRepository userRepository;

  @Mock private MimeMessage mimeMessage;

  @InjectMocks private MailService mailService;

  private User testUser;
  private static final String TEST_EMAIL = "test@example.com";

  @BeforeEach
  void setUp() {
    testUser = new User();
    testUser.setEmail(TEST_EMAIL);
    testUser.setFullname("Test User");

    // Set the required fields using reflection
    ReflectionTestUtils.setField(mailService, "emailFrom", "admin@example.com");
    ReflectionTestUtils.setField(mailService, "serverName", "testserver");
  }

  @Test
  void sendEmail_Success() throws MessagingException, UnsupportedEncodingException {
    // Arrange
    when(mailSender.createMimeMessage()).thenReturn(mimeMessage);
    when(templateEngine.process(anyString(), ArgumentMatchers.any()))
        .thenReturn("<html>Test template</html>");

    // Act
    CompletableFuture<String> result = mailService.sendEmail(testUser, TEST_EMAIL);

    // Assert
    assertNotNull(result);
    assertEquals("Sent", result.join());
    verify(mailSender).send(mimeMessage);

    // Verify OTP was set
    assertNotNull(testUser.getOTP());
    assertNotNull(testUser.getOtpExpirationDate());
    assertTrue(testUser.getOtpExpirationDate().after(new Date()));
  }

  @Test
  void sendEmail_ValidatesOTPLength() throws MessagingException, UnsupportedEncodingException {
    // Arrange
    when(mailSender.createMimeMessage()).thenReturn(mimeMessage);
    when(templateEngine.process(anyString(), ArgumentMatchers.any()))
        .thenReturn("<html>Test template</html>");

    // Act
    mailService.sendEmail(testUser, TEST_EMAIL);

    // Assert
    assertNotNull(testUser.getOTP());
    assertEquals(6, testUser.getOTP().length());
    assertTrue(testUser.getOTP().matches("\\d+"));
  }

  @Test
  void sendInvoice_Success() throws MessagingException, UnsupportedEncodingException {
    // Arrange
    Invoice invoice = new Invoice();
    Subject toSubject = new Subject();
    toSubject.setEmail(TEST_EMAIL);
    invoice.setTo(toSubject);

    when(mailSender.createMimeMessage()).thenReturn(mimeMessage);
    when(templateEngine.process(anyString(), ArgumentMatchers.any()))
        .thenReturn("<html>Invoice template</html>");

    // Act
    CompletableFuture<String> result = mailService.sendInvoice(invoice);

    // Assert
    assertNotNull(result);
    assertEquals("Sent", result.join());
    verify(mailSender).send(mimeMessage);
  }
}
