package com.example.nhom3_tt_.services.impl;

import com.example.nhom3_tt_.dtos.requests.OrderDetailRequest;
import com.example.nhom3_tt_.dtos.response.OrderDetailResponse;
import com.example.nhom3_tt_.exception.NotFoundException;
import com.example.nhom3_tt_.mappers.OrderDetailMapper;
import com.example.nhom3_tt_.models.Course;
import com.example.nhom3_tt_.models.Order;
import com.example.nhom3_tt_.models.OrderDetail;
import com.example.nhom3_tt_.models.User;
import com.example.nhom3_tt_.repositories.CourseRepository;
import com.example.nhom3_tt_.repositories.OrderDetailRepository;
import com.example.nhom3_tt_.repositories.OrderRepository;
import com.example.nhom3_tt_.repositories.UserRepository;
import com.example.nhom3_tt_.services.OrderDetailService;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderDetailServiceImpl implements OrderDetailService {

  private final OrderDetailRepository orderDetailRepository;
  private final OrderDetailMapper orderDetailMapper;
  private final OrderRepository orderRepository;
  private final CourseRepository courseRepository;
  private final UserRepository userRepository;

  @Transactional
  @Override
  public OrderDetailResponse create(OrderDetailRequest orderDetailRequest) {
    Long orderId = orderDetailRequest.getOrderId();
    Long courseId = orderDetailRequest.getCourseId();
    Order order =
        orderRepository
            .findById(orderId)
            .orElseThrow(() -> new NotFoundException("Order not found with id = " + orderId));
    Course course =
        courseRepository
            .findById(courseId)
            .orElseThrow(() -> new NotFoundException("Course not found with id=" + courseId));

    OrderDetail orderDetail = orderDetailMapper.convertToEntity(orderDetailRequest);

    Double price = course.getRegularPrice();
    orderDetail.setPrice(price);

    OrderDetail savedOrder = orderDetailRepository.save(orderDetail);
    return orderDetailMapper.convertToResponse(savedOrder);
  }

  @Override
  public List<OrderDetailResponse> getAll(Pageable pageable) {
    List<OrderDetailResponse> responses =
        orderDetailRepository.findAll(pageable).stream()
            .map(orderDetailMapper::convertToResponse)
            .toList();
    return responses.isEmpty() ? new ArrayList<>() : responses;
  }

  @Override
  public OrderDetailResponse getById(Long id) {
    OrderDetail orderDetail =
        orderDetailRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("OrderDetail not found with id=" + id));
    return orderDetailMapper.convertToResponse(orderDetail);
  }

  @Override
  public List<OrderDetailResponse> getByStudentId(Long studentId) {
    boolean existedUser = userRepository.existsById(studentId);
    if (!existedUser) {
      throw new NotFoundException("User cannot found with id = " + studentId);
    }
    return orderDetailRepository.findByOrderStudentId(studentId).stream()
        .map(orderDetailMapper::convertToResponse)
        .toList();
  }

  @Override
  public List<OrderDetailResponse> getByOrderId(Long id) {
    boolean isExistedOrder = orderRepository.existsById(id);
    if (!isExistedOrder) {
      throw new NotFoundException("Order cannot found with id = " + id);
    }
    return orderDetailRepository.findByOrderId(id).stream()
        .map(orderDetailMapper::convertToResponse)
        .toList();
  }

  @Override
  public List<OrderDetailResponse> myOrderDetails() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    User currenUser = (User) authentication.getPrincipal();
    return orderDetailRepository.findByOrderStudentId(currenUser.getId()).stream()
        .map(orderDetailMapper::convertToResponse)
        .toList();
  }

  @Transactional
  @Override
  public OrderDetailResponse update(Long id, OrderDetailRequest newOrderDetail) {
    OrderDetail orderDetail =
        orderDetailRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("OrderDetail not found with id=" + id));
    Long orderId = newOrderDetail.getOrderId();
    Order order =
        orderRepository
            .findById(orderId)
            .orElseThrow(() -> new NotFoundException("Order not found with id = " + orderId));
    Long courseId = newOrderDetail.getCourseId();
    Course course =
        courseRepository
            .findById(courseId)
            .orElseThrow(() -> new NotFoundException("Course not found with id=" + courseId));

    orderDetail.setOrder(order);
    orderDetail.setCourse(course);
    orderDetail.calculateAndSetPrice();

    return orderDetailMapper.convertToResponse(orderDetailRepository.save(orderDetail));
  }

  @Transactional
  @Override
  public OrderDetailResponse forceDelete(Long id) {
    OrderDetail orderDetail =
        orderDetailRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("OrderDetail not found with id=" + id));
    OrderDetailResponse response = orderDetailMapper.convertToResponse(orderDetail);
    orderDetailRepository.deleteById(id);
    // log.info("ForceDelete orderDetail with id= {}", id);
    return response;
  }
}
