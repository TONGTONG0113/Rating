package com.takima.backskeleton.controllers;

import com.takima.backskeleton.DTO.RestaurantDto;
import com.takima.backskeleton.skills.RestaurantRecommendationSkill;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/skills")
@CrossOrigin(origins = "http://localhost:4200")
public class SkillController {

    private final RestaurantRecommendationSkill
            restaurantRecommendationSkill;

    public SkillController(
            RestaurantRecommendationSkill
                    restaurantRecommendationSkill
    ) {
        this.restaurantRecommendationSkill =
                restaurantRecommendationSkill;
    }

    /**
     * Example:
     *
     * GET /api/skills/restaurants/recommend
     *     ?cuisine=Italian
     *     &minRating=4.0
     *     &limit=3
     */
    @GetMapping("/restaurants/recommend")
    public List<RestaurantDto> recommendRestaurants(
            @RequestParam(
                    required = false,
                    defaultValue = "All"
            )
            String cuisine,

            @RequestParam(
                    required = false,
                    defaultValue = "0"
            )
            double minRating,

            @RequestParam(
                    required = false,
                    defaultValue = "3"
            )
            int limit
    ) {
        return restaurantRecommendationSkill.recommend(
                cuisine,
                minRating,
                limit
        );
    }
}