package net.javaguides.CD_Web_backend.service;

import net.javaguides.CD_Web_backend.dto.ReviewDto;
import net.javaguides.CD_Web_backend.entity.Products;

import java.util.List;

public interface ReviewService {
    ReviewDto createReview(ReviewDto reviewDto);
    List<ReviewDto> getReviewsByProductId(Products products);
}
