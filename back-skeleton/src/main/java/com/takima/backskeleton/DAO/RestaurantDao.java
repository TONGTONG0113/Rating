package com.takima.backskeleton.DAO;

import com.takima.backskeleton.models.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantDao extends JpaRepository<Restaurant, Long> {
}