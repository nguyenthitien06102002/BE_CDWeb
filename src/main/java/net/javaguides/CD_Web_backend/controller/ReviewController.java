package net.javaguides.CD_Web_backend.controller;

import lombok.AllArgsConstructor;
import net.javaguides.CD_Web_backend.dto.ReviewDto;
import net.javaguides.CD_Web_backend.entity.Products;
import net.javaguides.CD_Web_backend.service.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.InetAddress;
import java.net.Socket;
import java.util.List;

@CrossOrigin("*")
@AllArgsConstructor
@RestController
@RequestMapping("/api/review")
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<ReviewDto> createReview(@RequestBody ReviewDto reviewDto) {
        String ipAddress = getClientIpAddress();
        reviewDto.setIp(ipAddress);
        ReviewDto savedReview = reviewService.createReview(reviewDto);
        return ResponseEntity.ok(savedReview);
    }

    @GetMapping("{productId}")
    public ResponseEntity<List<ReviewDto>> getReviewsByProductId(@PathVariable("productId") Products productId) {
        List<ReviewDto> reviewDtos = reviewService.getReviewsByProductId(productId);
        return ResponseEntity.ok(reviewDtos);
    }


    private String getClientIpAddress() {
        try {
            Socket socket = new Socket("google.com", 80); // Kết nối tạm thời đến một host bất kỳ
            InetAddress address = socket.getLocalAddress(); // Lấy địa chỉ IP của client từ socket
            return address.getHostAddress();
        } catch (Exception e) {
            return "Unknown"; // Trả về "Unknown" nếu không thể lấy được địa chỉ IP
        }
    }
}
