package com.microservice.reviewservice.review;

import java.util.List;

public interface ReviewService {
    List<Review> getAllReviews(Long companyId);

    Review createReview(Long companyId, Review review);

    Review getReviewById(Long companyId);

    boolean updateReview(Long reviewId, Review review);

    boolean deleteReview(Long reviewId);
}
