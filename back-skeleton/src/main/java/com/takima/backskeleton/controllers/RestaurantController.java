
package com.takima.backskeleton.controllers;

import com.takima.backskeleton.DTO.RestaurantDto;
import com.takima.backskeleton.services.RestaurantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    // Get one restaurant
    @GetMapping("/{id}")
    public RestaurantDto getRestaurantById(@PathVariable Long id) {
        return restaurantService.getRestaurantById(id);
    }

    // Create a restaurant
    @PostMapping
    public ResponseEntity<RestaurantDto> createRestaurant(
            @RequestBody RestaurantDto restaurantDto) {

        RestaurantDto created =
                restaurantService.createRestaurant(restaurantDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // Edit a restaurant
    @PutMapping("/{id}")
    public RestaurantDto updateRestaurant(
            @PathVariable Long id,
            @RequestBody RestaurantDto restaurantDto) {

        return restaurantService.updateRestaurant(id, restaurantDto);
    }

    // Delete a restaurant
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRestaurant(@PathVariable Long id) {
        restaurantService.deleteRestaurant(id);
        return ResponseEntity.noContent().build();
    }
}
