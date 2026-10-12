package com.takima.backskeleton.DTO;

public class RestaurantRankingDto {

    private Long restaurantId;
    private String restaurantName;
    private double averageRating;
    private long reviewCount;

    public RestaurantRankingDto(
            Long restaurantId,
            String restaurantName,
            double averageRating,
            long reviewCount
    ) {
        this.restaurantId = restaurantId;
        this.restaurantName = restaurantName;
        this.averageRating = Math.round(averageRating * 100.0) / 100.0;
        this.reviewCount = reviewCount;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public long getReviewCount() {
        return reviewCount;
    }
}