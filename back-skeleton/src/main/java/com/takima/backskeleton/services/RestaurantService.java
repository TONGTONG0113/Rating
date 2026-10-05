package com.takima.backskeleton.services;

import com.takima.backskeleton.DAO.RestaurantDao;
import org.springframework.stereotype.Service;

@Service
public class RestaurantService {

    private final RestaurantDao restaurantDao;

    public RestaurantService(RestaurantDao restaurantDao) {
        this.restaurantDao = restaurantDao;
    }
}