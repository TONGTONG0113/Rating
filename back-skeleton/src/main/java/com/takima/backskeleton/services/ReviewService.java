package com.takima.backskeleton.services;

import com.takima.backskeleton.DAO.AppUserDao;
import com.takima.backskeleton.DAO.RestaurantDao;
import com.takima.backskeleton.DAO.ReviewDao;
import com.takima.backskeleton.DTO.ReviewDto;
import com.takima.backskeleton.DTO.ReviewMapper;
import com.takima.backskeleton.models.AppUser;
import com.takima.backskeleton.models.Restaurant;
import com.takima.backskeleton.models.Review;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReviewService {

    private final ReviewDao reviewDao;
    private final AppUserDao appUserDao;
    private final RestaurantDao restaurantDao;

    public ReviewService(
            ReviewDao reviewDao,
            AppUserDao appUserDao,
            RestaurantDao restaurantDao
    ) {
        this.reviewDao = reviewDao;
        this.appUserDao = appUserDao;
        this.restaurantDao = restaurantDao;
    }

    public List<ReviewDto> getAllReviews() {
        return reviewDao.findAll()
                .stream()
                .map(ReviewMapper::toDto)
                .toList();
    }

    public ReviewDto getReviewById(Long id) {
        Review review = reviewDao.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        return ReviewMapper.toDto(review);
    }

    public ReviewDto createReview(ReviewDto dto) {

        if (dto.getRating() == null ||
                dto.getRating() < 1 ||
                dto.getRating() > 5) {
            throw new IllegalArgumentException(
                    "Rating must be between 1 and 5"
            );
        }

        AppUser user = appUserDao.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Restaurant restaurant = restaurantDao.findById(dto.getRestaurantId())
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));

        Review review = ReviewMapper.fromDto(
                dto,
                null,
                user,
                restaurant
        );

        review.setCreatedAt(LocalDateTime.now());

        Review savedReview = reviewDao.save(review);

        return ReviewMapper.toDto(savedReview);
    }

    public ReviewDto updateReview(Long id, ReviewDto dto) {

        Review existingReview = reviewDao.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        if (dto.getRating() == null ||
                dto.getRating() < 1 ||
                dto.getRating() > 5) {
            throw new IllegalArgumentException(
                    "Rating must be between 1 and 5"
            );
        }

        existingReview.setRating(dto.getRating());
        existingReview.setSummary(dto.getSummary());
        existingReview.setDetails(dto.getDetails());

        Review updatedReview = reviewDao.save(existingReview);

        return ReviewMapper.toDto(updatedReview);
    }

    public void deleteReview(Long id) {

        if (!reviewDao.existsById(id)) {
            throw new RuntimeException("Review not found");
        }

        reviewDao.deleteById(id);
    }

    public List<ReviewDto> getReviewsByRestaurant(Long restaurantId) {

        return reviewDao.findAll()
                .stream()
                .filter(review ->
                        review.getRestaurant().getId().equals(restaurantId)
                )
                .map(ReviewMapper::toDto)
                .toList();
    }

    public List<ReviewDto> getReviewsByUser(Long userId) {

        return reviewDao.findAll()
                .stream()
                .filter(review ->
                        review.getUser().getId().equals(userId)
                )
                .map(ReviewMapper::toDto)
                .toList();
    }
}