package com.takima.backskeleton.controllers;

import com.takima.backskeleton.DTO.RestaurantDto;
import com.takima.backskeleton.DTO.RestaurantRankingDto;
import com.takima.backskeleton.services.RestaurantService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
@CrossOrigin(origins = "http://localhost:4200")
public class RestaurantController {

    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping
    public List<RestaurantDto> getAllRestaurants() {
        return restaurantService.getAllRestaurants();
    }

    @GetMapping("/{id}")
    public RestaurantDto getRestaurantById(@PathVariable Long id) {
        return restaurantService.getRestaurantById(id);
    }

    @GetMapping("/ranking")
    public List<RestaurantRankingDto> getRestaurantRanking() {
        return restaurantService.getRestaurantRanking();
    }
}
