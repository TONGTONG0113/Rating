package com.takima.backskeleton.services;

import com.takima.backskeleton.DAO.ReviewDao;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {

    private final ReviewDao reviewDao;

    public ReviewService(ReviewDao reviewDao) {
        this.reviewDao = reviewDao;
    }
}