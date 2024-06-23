package net.javaguides.CD_Web_backend.service;

import net.javaguides.CD_Web_backend.dto.OrdersDto;
import net.javaguides.CD_Web_backend.entity.Users;

import java.util.List;
import java.util.Optional;

public interface OrdersService {
    OrdersDto createOrder(OrdersDto ordersDto);
    List<OrdersDto> getOrderByIdUser(Users usersId);
    OrdersDto updateStatus(Long orderId, Long statusOrder);
    List<OrdersDto> getAllOrders();
    Optional<OrdersDto> getOrderById(Long orderId);
}
