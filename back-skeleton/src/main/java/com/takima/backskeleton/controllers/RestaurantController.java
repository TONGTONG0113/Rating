
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

    // Get all restaurants
    @GetMapping
    public List<RestaurantDto> getAllRestaurants() {
        return restaurantService.getAllRestaurants();
    }

    // Get restaurant by ID
    @GetMapping("/{id}")
    public RestaurantDto getRestaurantById(@PathVariable Long id) {
        return restaurantService.getRestaurantById(id);
    }

    // Get restaurant ranking
    @GetMapping("/ranking")
    public List<RestaurantRankingDto> getRestaurantRanking() {
        return restaurantService.getRestaurantRanking();
    }

    // Create restaurant
    @PostMapping
    public RestaurantDto createRestaurant(
            @RequestBody RestaurantDto restaurantDto) {
        return restaurantService.createRestaurant(restaurantDto);
    }

    // Update restaurant
    @PutMapping("/{id}")
    public RestaurantDto updateRestaurant(
            @PathVariable Long id,
            @RequestBody RestaurantDto restaurantDto) {
        return restaurantService.updateRestaurant(id, restaurantDto);
    }

    // Delete restaurant
    @DeleteMapping("/{id}")
    public void deleteRestaurant(@PathVariable Long id) {
        restaurantService.deleteRestaurant(id);
    }
}
