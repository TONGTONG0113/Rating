
package com.takima.backskeleton.DTO;

import com.takima.backskeleton.models.Restaurant;

public class RestaurantMapper {

    public static Restaurant fromDto(RestaurantDto dto, Long id) {
        Restaurant restaurant = new Restaurant();
        restaurant.setId(id);
        restaurant.setTitle(dto.getTitle());
        restaurant.setAddress(dto.getAddress());
        restaurant.setOpeningHours(dto.getOpeningHours());
        restaurant.setImageUrl(dto.getImageUrl());
        return restaurant;
    }

    public static RestaurantDto toDto(Restaurant restaurant) {
        return new RestaurantDto.RestaurantDtoBuilder()
                .id(restaurant.getId())
                .title(restaurant.getTitle())
                .address(restaurant.getAddress())
                .openingHours(restaurant.getOpeningHours())
                .imageUrl(restaurant.getImageUrl())
                .build();
    }
}
