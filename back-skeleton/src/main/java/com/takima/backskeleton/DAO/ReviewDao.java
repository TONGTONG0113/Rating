package com.takima.backskeleton.DAO;

import com.takima.backskeleton.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewDao extends JpaRepository<Review, Long> {
}