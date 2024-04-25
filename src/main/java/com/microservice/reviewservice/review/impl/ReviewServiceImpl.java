package com.microservice.reviewservice.review.impl;

import com.project.jobapp.company.Company;
import com.project.jobapp.company.CompanyService;
import com.project.jobapp.review.Review;
import com.project.jobapp.review.ReviewRepository;
import com.project.jobapp.review.ReviewService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final CompanyService companyService;

    public ReviewServiceImpl(ReviewRepository reviewRepository, CompanyService companyService) {
        this.reviewRepository = reviewRepository;
        this.companyService = companyService;
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
     * @return
     */
    @Override
    public Review createReview(Long companyId, Review review) {
        Company company = companyService.getCompanyById(companyId);
        if (company != null) {
            review.setCompany(company);
            return reviewRepository.saveAndFlush(review);
        }
        return null;
    }

    /**
     * @param companyId
     * @param reviewId
     * @return
     */
    @Override
    public Review getReviewById(Long companyId, Long reviewId) {
        List<Review> reviews = reviewRepository.findByCompanyId(companyId);
        return reviews.stream().filter(review -> review.getId().equals(reviewId)).findFirst().orElse(null);
    }

    /**
     * @param companyId
     * @param reviewId
     * @param review
     * @return
     */
    @Override
    public boolean updateReview(Long companyId, Long reviewId, Review review) {
        Company company = companyService.getCompanyById(companyId);
        if (company != null) {
            review.setCompany(company);
            review.setId(reviewId);
            reviewRepository.save(review);
            return true;
        }
        return false;
    }

    /**
     * @param companyId Company id
     * @param reviewId Review id
     * @return boolean
     */
    @Override
    public boolean deleteReview(Long companyId, Long reviewId) {
        Review review = getReviewById(companyId, reviewId);
        if (review != null) {
            Company company = review.getCompany();
            company.getReviews().remove(review);
            companyService.updateCompanyById(companyId, company);
            reviewRepository.deleteById(reviewId);
            return true;
        }
        return false;
    }

    /*private void updateReviewDetails(Review response, Review review) {
        if (review.getTitle() != null) {
            response.setTitle(review.getTitle());
        }
        if (review.getDescription() != null) {
            response.setDescription(review.getDescription());
        }
        if (review.getRating() != null) {
            response.setRating(review.getRating());
        }
        reviewRepository.save(response);
    }*/
}
