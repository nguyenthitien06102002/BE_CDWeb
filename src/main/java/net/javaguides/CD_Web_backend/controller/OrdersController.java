package net.javaguides.CD_Web_backend.controller;


import lombok.AllArgsConstructor;
import net.javaguides.CD_Web_backend.dto.OrdersDto;
import net.javaguides.CD_Web_backend.entity.Users;
import net.javaguides.CD_Web_backend.service.OrdersService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.InetAddress;
import java.net.Socket;
import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@AllArgsConstructor
@RestController
@RequestMapping("/api/orders")
public class OrdersController {
    private final OrdersService ordersService;

    @PostMapping
    public ResponseEntity<OrdersDto> createOrders(@RequestBody OrdersDto ordersDto){
        String ipAddress = getClientIpAddress();
        ordersDto.setIp(ipAddress);
        OrdersDto savedOrders = ordersService.createOrder(ordersDto);
        return new ResponseEntity<>(savedOrders, HttpStatus.CREATED);
    }

    @GetMapping("{userId}")
    public ResponseEntity<List<OrdersDto>> getOrderByUserId(@PathVariable("userId") Users userId){
        List<OrdersDto> ordersDtos = ordersService.getOrderByIdUser(userId);
        return ResponseEntity.ok(ordersDtos);
    }
    @PutMapping("updateStatus/{orderId}")
    public ResponseEntity<?> updateStatusOrder(@PathVariable("orderId") Long orderId,@RequestParam Long statusId){
        ordersService.updateStatus(orderId, statusId);
        return ResponseEntity.ok("Status updated successfully");
    }


    private String getClientIpAddress() {
        try {
            Socket socket = new Socket("google.com", 80);
            InetAddress address = socket.getLocalAddress();
            return address.getHostAddress();
        } catch (Exception e) {
            return "Unknown";
        }
    }
    @GetMapping
    public ResponseEntity<List<OrdersDto>> getAllOrders() {
        List<OrdersDto> ordersDtos = ordersService.getAllOrders();
        return ResponseEntity.ok(ordersDtos);
    }
    @GetMapping("/id={orderId}")
    public ResponseEntity<OrdersDto> getOrderById(@PathVariable Long orderId) {
        Optional<OrdersDto> orderDtoOptional = ordersService.getOrderById(orderId);
        return orderDtoOptional.map(orderDto -> new ResponseEntity<>(orderDto, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }




}
