package com.takima.backskeleton.services;

import com.takima.backskeleton.DAO.AppUserDao;
import com.takima.backskeleton.DAO.RestaurantDao;
import com.takima.backskeleton.DAO.ReviewDao;
import com.takima.backskeleton.DTO.ReviewDto;
import com.takima.backskeleton.DTO.ReviewMapper;
import com.takima.backskeleton.models.AppUser;
import com.takima.backskeleton.models.Restaurant;
import com.takima.backskeleton.models.Review;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

        return reviewDao
                .findAll()
                .stream()
                .map(ReviewMapper::toDto)
                .toList();
    }

    public ReviewDto getReviewById(Long id) {

        return ReviewMapper.toDto(
                getReviewOrThrow(id)
        );
    }

    public ReviewDto createReview(ReviewDto dto) {

        validateReview(dto);

        AppUser user = appUserDao
                .findById(dto.getUserId())
                .orElseThrow(
                        () ->
                                new ResponseStatusException(
                                        HttpStatus.NOT_FOUND,
                                        "User not found"
                                )
                );

        Restaurant restaurant = restaurantDao
                .findById(dto.getRestaurantId())
                .orElseThrow(
                        () ->
                                new ResponseStatusException(
                                        HttpStatus.NOT_FOUND,
                                        "Restaurant not found"
                                )
                );

        Review review = ReviewMapper.fromDto(
                dto,
                null,
                user,
                restaurant
        );

        review.setCreatedAt(
                LocalDateTime.now()
        );

        Review savedReview =
                reviewDao.save(review);

        return ReviewMapper.toDto(
                savedReview
        );
    }

    public ReviewDto updateReview(
            Long id,
            ReviewDto dto
    ) {

        Review existingReview =
                getReviewOrThrow(id);

        validateReviewContent(dto);

        existingReview.setRating(
                dto.getRating()
        );

        existingReview.setSummary(
                dto.getSummary()
        );

        existingReview.setDetails(
                dto.getDetails()
        );

        Review updatedReview =
                reviewDao.save(
                        existingReview
                );

        return ReviewMapper.toDto(
                updatedReview
        );
    }

    public void deleteReview(Long id) {

        Review review =
                getReviewOrThrow(id);

        reviewDao.delete(review);
    }

    public List<ReviewDto> getReviewsByRestaurant(
            Long restaurantId
    ) {

        if (!restaurantDao.existsById(restaurantId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Restaurant not found"
            );
        }

        return reviewDao
                .findByRestaurantIdOrderByCreatedAtDesc(
                        restaurantId
                )
                .stream()
                .map(ReviewMapper::toDto)
                .toList();
    }

    public List<ReviewDto> getReviewsByUser(
            Long userId
    ) {

        if (!appUserDao.existsById(userId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "User not found"
            );
        }

        return reviewDao
                .findByUserIdOrderByCreatedAtDesc(
                        userId
                )
                .stream()
                .map(ReviewMapper::toDto)
                .toList();
    }

    private Review getReviewOrThrow(
            Long id
    ) {

        return reviewDao
                .findById(id)
                .orElseThrow(
                        () ->
                                new ResponseStatusException(
                                        HttpStatus.NOT_FOUND,
                                        "Review not found"
                                )
                );
    }

    private void validateReview(
            ReviewDto dto
    ) {

        validateReviewContent(dto);

        if (dto.getUserId() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "User is required"
            );
        }

        if (dto.getRestaurantId() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Restaurant is required"
            );
        }
    }

    private void validateReviewContent(
            ReviewDto dto
    ) {

        if (
                dto.getRating() == null ||
                        dto.getRating() < 1 ||
                        dto.getRating() > 5
        ) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Rating must be between 1 and 5"
            );
        }

        if (
                dto.getSummary() == null ||
                        dto.getSummary().isBlank()
        ) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Summary is required"
            );
        }
    }
}