package com.takima.backskeleton.DTO;

import java.time.LocalDateTime;

public class ReviewDto {

    private Long id;
    private Integer rating;
    private String summary;
    private String details;
    private LocalDateTime createdAt;
    private Long userId;
    private Long restaurantId;

    public Long getId() {
        return id;
    }

    public Integer getRating() {
        return rating;
    }

    public String getSummary() {
        return summary;
    }

    public String getDetails() {
        return details;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public static final class ReviewDtoBuilder {

        private Long id;
        private Integer rating;
        private String summary;
        private String details;
        private LocalDateTime createdAt;
        private Long userId;
        private Long restaurantId;

        public ReviewDtoBuilder() {
        }

        public static ReviewDtoBuilder aReviewDto() {
            return new ReviewDtoBuilder();
        }

        public ReviewDtoBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public ReviewDtoBuilder rating(Integer rating) {
            this.rating = rating;
            return this;
        }

        public ReviewDtoBuilder summary(String summary) {
            this.summary = summary;
            return this;
        }

        public ReviewDtoBuilder details(String details) {
            this.details = details;
            return this;
        }

        public ReviewDtoBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public ReviewDtoBuilder userId(Long userId) {
            this.userId = userId;
            return this;
        }

        public ReviewDtoBuilder restaurantId(Long restaurantId) {
            this.restaurantId = restaurantId;
            return this;
        }

        public ReviewDto build() {
            ReviewDto reviewDto = new ReviewDto();

            reviewDto.id = this.id;
            reviewDto.rating = this.rating;
            reviewDto.summary = this.summary;
            reviewDto.details = this.details;
            reviewDto.createdAt = this.createdAt;
            reviewDto.userId = this.userId;
            reviewDto.restaurantId = this.restaurantId;

            return reviewDto;
        }
    }
}
