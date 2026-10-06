package com.takima.backskeleton.controllers;

import com.takima.backskeleton.DTO.ReviewDto;
import com.takima.backskeleton.services.ReviewService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping
    public List<ReviewDto> getAllReviews() {
        return reviewService.getAllReviews();
    }

    @GetMapping("/{id}")
    public ReviewDto getReviewById(@PathVariable Long id) {
        return reviewService.getReviewById(id);
    }

    @PostMapping
    public ReviewDto createReview(@RequestBody ReviewDto reviewDto) {
        return reviewService.createReview(reviewDto);
    }

    @PutMapping("/{id}")
    public ReviewDto updateReview(
            @PathVariable Long id,
            @RequestBody ReviewDto reviewDto
    ) {
        return reviewService.updateReview(id, reviewDto);
    }

    @DeleteMapping("/{id}")
    public void deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
    }

    @GetMapping("/restaurant/{restaurantId}")
    public List<ReviewDto> getReviewsByRestaurant(
            @PathVariable Long restaurantId
    ) {
        return reviewService.getReviewsByRestaurant(restaurantId);
    }

    @GetMapping("/user/{userId}")
    public List<ReviewDto> getReviewsByUser(
            @PathVariable Long userId
    ) {
        return reviewService.getReviewsByUser(userId);
    }
}