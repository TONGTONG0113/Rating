package com.takima.backskeleton.DTO;

import java.time.LocalDateTime;

public class ReviewDto {

    private Long id;
    private Integer rating;
    private String summary;
    private String details;
    private LocalDateTime createdAt;

    private Long userId;
    private String userName;

    private Long restaurantId;
    private String restaurantTitle;

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

    public String getUserName() {
        return userName;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public String getRestaurantTitle() {
        return restaurantTitle;
    }

    public static final class ReviewDtoBuilder {

        private Long id;
        private Integer rating;
        private String summary;
        private String details;
        private LocalDateTime createdAt;

        private Long userId;
        private String userName;

        private Long restaurantId;
        private String restaurantTitle;

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

        public ReviewDtoBuilder userName(String userName) {
            this.userName = userName;
            return this;
        }

        public ReviewDtoBuilder restaurantId(Long restaurantId) {
            this.restaurantId = restaurantId;
            return this;
        }

        public ReviewDtoBuilder restaurantTitle(String restaurantTitle) {
            this.restaurantTitle = restaurantTitle;
            return this;
        }

        public ReviewDto build() {
            ReviewDto dto = new ReviewDto();

            dto.id = this.id;
            dto.rating = this.rating;
            dto.summary = this.summary;
            dto.details = this.details;
            dto.createdAt = this.createdAt;

            dto.userId = this.userId;
            dto.userName = this.userName;

            dto.restaurantId = this.restaurantId;
            dto.restaurantTitle = this.restaurantTitle;

            return dto;
        }
    }
}