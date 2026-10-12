package com.takima.backskeleton.services;

import com.takima.backskeleton.DAO.RestaurantDao;
import com.takima.backskeleton.DTO.RestaurantDto;
import com.takima.backskeleton.DTO.RestaurantMapper;
import com.takima.backskeleton.models.Restaurant;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantService {

    private final RestaurantDao restaurantDao;

    public RestaurantService(RestaurantDao restaurantDao) {
        this.restaurantDao = restaurantDao;
    }

    public List<RestaurantDto> getAllRestaurants() {
        return restaurantDao.findAll()
                .stream()
                .map(RestaurantMapper::toDto)
                .toList();
    }

    public RestaurantDto getRestaurantById(Long id) {
        Restaurant restaurant = restaurantDao.findById(id)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));

        return RestaurantMapper.toDto(restaurant);
    }
}