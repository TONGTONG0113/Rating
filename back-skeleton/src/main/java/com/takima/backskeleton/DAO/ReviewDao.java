package com.takima.backskeleton.DAO;

import com.takima.backskeleton.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewDao extends JpaRepository<Review, Long> {

    List<Review> findByUserIdOrderByCreatedAtDesc(Long userId);

    List<Review> findByRestaurantIdOrderByCreatedAtDesc(Long restaurantId);

    @Query("""
        SELECT r.restaurant.id, r.restaurant.title, AVG(r.rating), COUNT(r.id)
        FROM Review r
        GROUP BY r.restaurant.id, r.restaurant.title
        ORDER BY AVG(r.rating) DESC
    """)
    List<Object[]> findRestaurantRanking();
}