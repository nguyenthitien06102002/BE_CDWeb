package net.javaguides.CD_Web_backend.repository;

import net.javaguides.CD_Web_backend.entity.Products;
import net.javaguides.CD_Web_backend.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByProductId(Products productId);
}
