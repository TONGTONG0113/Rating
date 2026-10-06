package com.takima.backskeleton.DAO;

import com.takima.backskeleton.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewDao extends JpaRepository<Review, Long> {

    List<Review> findByUserIdOrderByCreatedAtDesc(Long userId);

    List<Review> findByRestaurantIdOrderByCreatedAtDesc(Long restaurantId);
}