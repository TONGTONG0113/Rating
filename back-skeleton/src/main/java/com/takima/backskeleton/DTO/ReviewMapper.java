package com.takima.backskeleton.DTO;

import com.takima.backskeleton.models.AppUser;
import com.takima.backskeleton.models.Restaurant;
import com.takima.backskeleton.models.Review;

public class ReviewMapper {

    public static Review fromDto(
            ReviewDto dto,
            Long id,
            AppUser user,
            Restaurant restaurant
    ) {
        Review review = new Review();

        review.setId(id);
        review.setRating(dto.getRating());
        review.setSummary(dto.getSummary());
        review.setDetails(dto.getDetails());
        review.setCreatedAt(dto.getCreatedAt());
        review.setUser(user);
        review.setRestaurant(restaurant);

        return review;
    }

    public static ReviewDto toDto(Review review) {
        return new ReviewDto.ReviewDtoBuilder()
                .id(review.getId())
                .rating(review.getRating())
                .summary(review.getSummary())
                .details(review.getDetails())
                .createdAt(review.getCreatedAt())
                .userId(review.getUser().getId())
                .restaurantId(review.getRestaurant().getId())
                .build();
    }
}