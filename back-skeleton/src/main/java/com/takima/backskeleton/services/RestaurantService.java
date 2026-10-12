package com.takima.backskeleton.services;

import com.takima.backskeleton.DAO.RestaurantDao;
import com.takima.backskeleton.DAO.ReviewDao;
import com.takima.backskeleton.DTO.RestaurantDto;
import com.takima.backskeleton.DTO.RestaurantMapper;
import com.takima.backskeleton.DTO.RestaurantRankingDto;
import com.takima.backskeleton.models.Restaurant;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantService {

    private final RestaurantDao restaurantDao;
    private final ReviewDao reviewDao;

    public RestaurantService(
            RestaurantDao restaurantDao,
            ReviewDao reviewDao
    ) {
        this.restaurantDao = restaurantDao;
        this.reviewDao = reviewDao;
    }

    public List<RestaurantDto> getAllRestaurants() {
        return restaurantDao.findAll()
                .stream()
                .map(RestaurantMapper::toDto)
                .toList();
    }

    public RestaurantDto getRestaurantById(Long id) {
        Restaurant restaurant = restaurantDao.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Restaurant not found"));

        return RestaurantMapper.toDto(restaurant);
    }

    public List<RestaurantRankingDto> getRestaurantRanking() {
        return reviewDao.findRestaurantRanking()
                .stream()
                .map(row -> new RestaurantRankingDto(
                        ((Number) row[0]).longValue(),
                        (String) row[1],
                        ((Number) row[2]).doubleValue(),
                        ((Number) row[3]).longValue()
                ))
                .toList();
    }
}