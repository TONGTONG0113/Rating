package com.takima.backskeleton.skills;

import com.takima.backskeleton.DTO.RestaurantDto;
import com.takima.backskeleton.services.RestaurantService;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class RestaurantRecommendationSkill {

    private final RestaurantService restaurantService;

    public RestaurantRecommendationSkill(
            RestaurantService restaurantService
    ) {
        this.restaurantService = restaurantService;
    }

    /**
     * Recommends restaurants using existing application data.
     *
     * Ranking rules:
     * 1. Filter by cuisine when one is provided.
     * 2. Filter by minimum rating.
     * 3. Sort by average rating descending.
     * 4. When ratings are equal, prefer restaurants with more reviews.
     * 5. Limit the number of returned restaurants.
     */
    public List<RestaurantDto> recommend(
            String cuisine,
            double minRating,
            int limit
    ) {
        int safeLimit = Math.max(1, Math.min(limit, 10));

        return restaurantService.getAllRestaurants()
                .stream()
                .filter(restaurant ->
                        matchesCuisine(
                                restaurant,
                                cuisine
                        )
                )
                .filter(restaurant ->
                        restaurant.getReviewCount() != null
                                && restaurant.getReviewCount() > 0
                )
                .filter(restaurant ->
                        restaurant.getAverageRating() != null
                                && restaurant.getAverageRating() >= minRating
                )
                .sorted(
                        Comparator
                                .comparing(
                                        RestaurantDto::getAverageRating,
                                        Comparator.reverseOrder()
                                )
                                .thenComparing(
                                        RestaurantDto::getReviewCount,
                                        Comparator.reverseOrder()
                                )
                )
                .limit(safeLimit)
                .toList();
    }

    private boolean matchesCuisine(
            RestaurantDto restaurant,
            String cuisine
    ) {
        if (cuisine == null
                || cuisine.isBlank()
                || cuisine.equalsIgnoreCase("All")) {
            return true;
        }

        return restaurant.getCuisine() != null
                && restaurant.getCuisine()
                .equalsIgnoreCase(cuisine.trim());
    }
}