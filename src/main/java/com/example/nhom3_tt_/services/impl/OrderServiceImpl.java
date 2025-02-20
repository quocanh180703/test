package com.example.nhom3_tt_.services.impl;

import com.example.nhom3_tt_.dtos.requests.OrderDetailRequest;
import com.example.nhom3_tt_.dtos.response.*;
import com.example.nhom3_tt_.dtos.response.earningAnalytic.EarningAnalyticResponse;
import com.example.nhom3_tt_.exception.AppException;
import com.example.nhom3_tt_.exception.ErrorCode;
import com.example.nhom3_tt_.mappers.EarningMapper;
import com.example.nhom3_tt_.mappers.OrderDetailMapper;
import com.example.nhom3_tt_.mappers.OrderMapper;
import com.example.nhom3_tt_.models.*;
import com.example.nhom3_tt_.repositories.*;
import com.example.nhom3_tt_.services.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import com.google.googlejavaformat.Op;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

  private final OrderRepository orderRepository;
  private final OrderDetailRepository orderDetailRepository;
  private final UserRepository userRepository;
  private final CourseRepository courseRepository;
  private final OrderMapper orderMapper;
  private final OrderDetailMapper orderDetailMapper;
  private final CartRepository cartRepository;
  private final CartService cartService;
  private final CouponService couponService;
  private final OrderDetailService orderDetailService;
  private final CartItemRepository cartItemRepository;
  private final EnrollRepository enrollRepository;
  private CartItemService cartItemService;
  private final EarningMapper earningMapper;

  @Override
  public String getAmount(User user, List<String> couponCodes, String listProduct) {
    Cart cart = cartRepository
            .findByStudentId(user.getId())
            .orElseThrow(() -> new AppException(ErrorCode.CART_NOT_FOUND));
    List<CartItem> cartItems = cart.getCartItems();
    if (cartItems == null || cartItems.isEmpty()) {
      throw new AppException(ErrorCode.CART_ITEM_EMPTY);
    }
    List<Integer> productIds = Arrays.stream(listProduct.split(","))
            .map(Integer::parseInt)
            .toList();

    // Kiêm tra khóa học đã tồn tại và đã có trong cartitem hay chưa
    for(Integer productId : productIds) {
      //lấy khóa học thoe id truyền từ ngoài vào
      Optional<Course> courseOptional = courseRepository.findById(Long.valueOf(productId));
      if(courseOptional.isEmpty()) {
        throw new AppException(ErrorCode.COURSE_NOT_FOUND);
      }
      Course course = courseOptional.get();
      //
      Optional<CartItem> cartItemOptional =  cartItemRepository.findByCourseAndCart(course,cart);
      if(cartItemOptional.isEmpty()) {
        throw new AppException(ErrorCode.CART_ITEM_NOT_FOUND);
      }
    }

    double totalPrice = 0.0;

    // Calculate total price of cart items
    for (CartItem cartItem : cartItems) {
      Course course = cartItem.getCourse();
      Integer courseId = course.getId().intValue();

      if (productIds.contains(courseId)) {
        // Nếu có, tính giá của sản phẩm này
        double regularPrice = course.getRegularPrice();
        totalPrice += regularPrice;
      }
    }

    // áp mã giảm giá (nếu có)
    if (couponCodes != null && !couponCodes.isEmpty()) {
      // Áp dụng từng mã giảm giá từ danh sách
      for (String couponCode : couponCodes) {
        CouponResponse coupon = couponService.getByCode(couponCode);
        double discountPercent = coupon.getPercentDiscount();
        totalPrice -= totalPrice * (discountPercent / 100);
      }
    }
    // Format the total price with two decimal places
    String formattedPrice = String.format("%.2f", totalPrice);

    // Remove ".00" if the price is an integer
    if (formattedPrice.endsWith(".00")) {
      formattedPrice = formattedPrice.substring(0, formattedPrice.length() - 3);
    }

    return formattedPrice;
  }

  @Override
  public OrderResponse createOrder(User user, String vnp_Amount, String listProduct) {
    // Parse product IDs từ danh sách
    List<Integer> productIds;
    try {
      productIds = Arrays.stream(listProduct.split(","))
              .map(Integer::parseInt)
              .toList();
    } catch (NumberFormatException e) {
      throw new AppException(ErrorCode.INVALID_PRODUCT_ID_FORMAT);
    }

    // Lấy thông tin giỏ hàng của user
    Cart cart = cartRepository.findByStudentId(user.getId())
            .orElseThrow(() -> new AppException(ErrorCode.CART_NOT_FOUND));

    // Kiểm tra nếu giỏ hàng trống
    if (cart.getCartItems() == null || cart.getCartItems().isEmpty()) {
      throw new AppException(ErrorCode.CART_ITEM_EMPTY);
    }

    List<OrderDetail> orderDetails = new ArrayList<>();
    List<CartItem> cartItems = new ArrayList<>();

    for (Integer productId : productIds) {
      // Lấy thông tin khóa học từ repository
      Course course = courseRepository.findById(Long.valueOf(productId))
              .orElseThrow(() -> new AppException(ErrorCode.COURSE_NOT_FOUND));

      // Lấy cart item tương ứng với khóa học
      CartItem cartItem = cartItemRepository.findByCourseAndCart(course,cart)
              .orElseThrow(() -> new AppException(ErrorCode.CART_ITEM_NOT_FOUND));

      // Thêm vào danh sách cartItems
      cartItems.add(cartItem);
    }

    // Lấy thông tin người dùng (student)
    var student = userRepository.findById(user.getId())
            .orElseThrow(() -> new IllegalArgumentException("Student not found"));

    // Tạo đối tượng Order
    Order order = Order.builder()
            .student(student)
            .orderDate(new Date())
            .totalAmount(0.0)
            .orderDetails(new ArrayList<>())
            .build();
    order.setCreatedBy(user.getId());

    // Xử lý từng CartItem để tạo OrderDetail và Enroll
    for (CartItem cartItem : cartItems) {
      Course course = cartItem.getCourse();
      Double price = course.getRegularPrice();

      // Tạo OrderDetail
      OrderDetail orderDetail = OrderDetail.builder()
              .course(course)
              .order(order)
              .price(price)
              .build();
      orderDetail.setCreatedBy(user.getId());
      orderDetails.add(orderDetail);

      // Tạo Enroll
      Enroll enroll = Enroll.builder()
              .student(student)
              .course(course)
              .build();
      enroll.setCreatedBy(user.getId());
      enrollRepository.save(enroll);
    }

    // Xóa các sản phẩm có trong listProduct khỏi giỏ hàng
    cart.getCartItems().removeIf(cartItem ->
            productIds.contains(cartItem.getCourse().getId().intValue())
    );
    cartRepository.save(cart);

    // Hoàn tất thông tin đơn hàng
    order.setOrderDetails(orderDetails);
    order.setTotalAmount(Double.parseDouble(vnp_Amount)); // Chuyển đổi số tiền thành kiểu số thực
    Order savedOrder = orderRepository.save(order);

    return orderMapper.toOrderResponse(savedOrder);
  }


  @Override
  public OrderResponse getOrderById(Long orderId) {
    Order order =
        orderRepository
            .findById(orderId)
            .orElseThrow(() -> new IllegalArgumentException("Order not found"));
    return orderMapper.toOrderResponse(order);
  }

  @Override
  public Order getOrderEntityById(Long orderId) {
    return orderRepository
        .findById(orderId)
        .orElseThrow(() -> new IllegalArgumentException("Order not found"));
  }

  @Override
  public List<OrderResponse> getAllOrdersByStudentId(Long studentId) {
    User student =
        userRepository
            .findById(studentId)
            .orElseThrow(() -> new IllegalArgumentException("Student not found"));
    var orders = orderRepository.findByStudent_Id(studentId);

    if (orders.isEmpty()) {
      throw new IllegalArgumentException("No orders found for student with ID: " + studentId);
    }
    return orders.stream().map(orderMapper::toOrderResponse).toList();
  }

  @Override
  public OrderDetailResponse addOrderDetail(OrderDetailRequest orderDetailRequest) {
    var order =
        orderRepository
            .findById(orderDetailRequest.getOrderId())
            .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));

    var course =
        courseRepository
            .findById(orderDetailRequest.getCourseId())
            .orElseThrow(() -> new AppException(ErrorCode.COURSE_NOT_FOUND));

    OrderDetail orderDetail = OrderDetail.builder().order(order).course(course).build();
    orderDetail.calculateAndSetPrice();

    OrderDetail savedOrderDetail = orderDetailRepository.save(orderDetail);

    // Update total amount
    order.setTotalAmount(order.getTotalAmount() + savedOrderDetail.getPrice());
    orderRepository.save(order);

    return orderDetailMapper.convertToResponse(savedOrderDetail);
  }

  @Override
  public void deleteOrderByOrderId(Long orderId) {
    var order =
        orderRepository
            .findById(orderId)
            .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));

    var orderDetails = order.getOrderDetails();
    if (orderDetails != null && !orderDetails.isEmpty()) {
      orderDetailRepository.deleteAll(orderDetails);
    }

    orderRepository.delete(order);
  }

  @Override
  public void deleteOrderDetailById(Long orderDetailId) {
    var orderDetail =
        orderDetailRepository
            .findById(orderDetailId)
            .orElseThrow(() -> new AppException(ErrorCode.ORDER_DETAIL_NOT_FOUND));

    Order order = orderDetail.getOrder();
    order.setTotalAmount(order.getTotalAmount() - orderDetail.getPrice());
    orderRepository.save(order);
    orderDetailRepository.delete(orderDetail);
  }

  @Override
  public List<EarningAnalyticResponse> getEarning() {
    List<EarningAnalyticResponse> earningAnalyticResponses = new ArrayList<>();
    List<Order> listOrder = orderRepository.findAll();
    for (Order order : listOrder) {
      EarningAnalyticResponse temp = earningMapper.toEarningAnalyticResponse(order);
      earningAnalyticResponses.add(temp);
    }
    return earningAnalyticResponses;
  }

  @Override
  public Double getEarningTotal() {
    Double total = 0.0;
    List<Order> listOrder = orderRepository.findAll();
    for (Order order : listOrder) {
      EarningAnalyticResponse temp = earningMapper.toEarningAnalyticResponse(order);
      total += temp.getTotalAmount();
    }
    return total;
  }

  @Override
  public Double getEarningByDay(String date) {
    // Chuyển kiểu String thành LocalDate
    LocalDate localDate = convertStringToLocalDate(date);
    Double total = 0.0;
    List<Order> listOrder = orderRepository.findAllByCreatedAt(localDate);
    for (Order order : listOrder) {
      EarningAnalyticResponse temp = earningMapper.toEarningAnalyticResponse(order);
      total += temp.getTotalAmount();
    }
    return total;
  }

  @Override
  public Double getEarningByMonth(String month) {
    Double total = 0.0;
    List<Order> listOrder =
        orderRepository.findAllByCreatedAtMonth(
            month); // Bạn cần thay đổi method tìm kiếm theo tháng trong repository
    for (Order order : listOrder) {
      EarningAnalyticResponse temp = earningMapper.toEarningAnalyticResponse(order);
      total += temp.getTotalAmount();
    }
    return total;
  }

  @Override
  public Double getEarningByYear(String year) {
    Double total = 0.0;
    List<Order> listOrder = orderRepository.findAllByCreatedAtYear(year);
    for (Order order : listOrder) {
      EarningAnalyticResponse temp = earningMapper.toEarningAnalyticResponse(order);
      total += temp.getTotalAmount();
    }
    return total;
  }

  public LocalDate convertStringToLocalDate(String date) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    return LocalDate.parse(date, formatter);
  }
}
