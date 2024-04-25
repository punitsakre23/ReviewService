package com.microservice.reviewservice.review.impl;

import com.microservice.reviewservice.review.Review;
import com.microservice.reviewservice.review.ReviewRepository;
import com.microservice.reviewservice.review.ReviewService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    /**
     * @param companyId Company id
     * @return list of reviews
     */
    @Override
    public List<Review> getAllReviews(Long companyId) {
        return reviewRepository.findByCompanyId(companyId);
    }

    /**
     * @param companyId Company id
     * @param review    Review
     * @return Review
     */
    @Override
    public Review createReview(Long companyId, Review review) {
        if (companyId != null && review != null) {
            review.setCompanyId(companyId);
            return reviewRepository.saveAndFlush(review);
        }
        return null;
    }

    /**
     * @param reviewId Review id
     * @return Review
     */
    @Override
    public Review getReviewById(Long reviewId) {
        return reviewRepository.findById(reviewId).orElseThrow(() -> new NoSuchElementException("Review Not Found!"));
    }

    /**
     * @param reviewId Review id
     * @param review Review
     * @return boolean
     */
    @Override
    public boolean updateReview(Long reviewId, Review review) {
        Review review1 = reviewRepository.findById(reviewId).orElse(null);
        if (review1 != null) {
            review1.setTitle(review.getTitle());
            review1.setDescription(review.getDescription());
            review1.setCompanyId(review.getCompanyId());
            review1.setRating(review.getRating());
            reviewRepository.save(review1);
            return true;
        }
        return false;
    }

    /**
     * @param reviewId Review id
     * @return boolean
     */
    @Override
    public boolean deleteReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId).orElse(null);
        if (review != null) {
            reviewRepository.deleteById(reviewId);
            return true;
        }
        return false;
    }
}
