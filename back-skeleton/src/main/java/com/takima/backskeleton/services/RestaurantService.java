
package com.takima.backskeleton.services;

import com.takima.backskeleton.DAO.RestaurantDao;
import com.takima.backskeleton.DTO.RestaurantDto;
import com.takima.backskeleton.DTO.RestaurantMapper;
import com.takima.backskeleton.models.Restaurant;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class RestaurantService {

    private final RestaurantDao restaurantDao;

    public RestaurantService(RestaurantDao restaurantDao) {
        this.restaurantDao = restaurantDao;
    }

    // Get all restaurants
    public List<RestaurantDto> getAllRestaurants() {
        return restaurantDao.findAll()
                .stream()
                .map(RestaurantMapper::toDto)
                .toList();
    }

    // Get one restaurant
    public RestaurantDto getRestaurantById(Long id) {
        Restaurant restaurant = restaurantDao.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Restaurant not found: " + id
                ));

        return RestaurantMapper.toDto(restaurant);
    }

    // Create a restaurant
    @Transactional
    public RestaurantDto createRestaurant(RestaurantDto dto) {
        validateRestaurant(dto);

        Restaurant restaurant = RestaurantMapper.fromDto(dto, null);
        restaurant.setId(null);

        Restaurant saved = restaurantDao.save(restaurant);

        return RestaurantMapper.toDto(saved);
    }

    // Edit a restaurant
    @Transactional
    public RestaurantDto updateRestaurant(Long id, RestaurantDto dto) {
        validateRestaurant(dto);

        Restaurant existing = restaurantDao.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Restaurant not found: " + id
                ));

        existing.setTitle(dto.getTitle().trim());
        existing.setAddress(dto.getAddress());
        existing.setOpeningHours(dto.getOpeningHours());
        existing.setImageUrl(dto.getImageUrl());

        Restaurant saved = restaurantDao.save(existing);

        return RestaurantMapper.toDto(saved);
    }

    // Delete a restaurant
    @Transactional
    public void deleteRestaurant(Long id) {
        Restaurant existing = restaurantDao.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Restaurant not found: " + id
                ));

        restaurantDao.delete(existing);
    }

    private void validateRestaurant(RestaurantDto dto) {
        if (dto == null
                || dto.getTitle() == null
                || dto.getTitle().trim().isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Restaurant title is required"
            );
        }
    }
}
