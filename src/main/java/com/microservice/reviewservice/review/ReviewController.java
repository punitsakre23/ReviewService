package com.microservice.reviewservice.review;

import com.microservice.reviewservice.review.messaging.ReviewMessageProducer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;
    private final ReviewMessageProducer reviewMessageProducer;

    public ReviewController(ReviewService reviewService, ReviewMessageProducer reviewMessageProducer) {
        this.reviewService = reviewService;
        this.reviewMessageProducer = reviewMessageProducer;
    }

    @GetMapping
    public ResponseEntity<List<Review>> getAllReviews(@RequestParam Long companyId) {
        return ResponseEntity.ok(reviewService.getAllReviews(companyId));
    }

    @PostMapping
    public ResponseEntity<String> addReview(@RequestParam Long companyId, @RequestBody Review review) {
        Review createdReview = reviewService.createReview(companyId, review);
        if (createdReview != null) {
            reviewMessageProducer.sendMessage(review);
            return ResponseEntity.ok("Review added Successfully");
        }
        return new ResponseEntity<>("Review Not Saved", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<Review> getReviewById(@PathVariable Long reviewId) {
        return ResponseEntity.ok(reviewService.getReviewById(reviewId));
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<String> updateReview(@PathVariable Long reviewId, @RequestBody Review review) {
            boolean updated = reviewService.updateReview(reviewId, review);
        if (updated) {
            return ResponseEntity.ok("Review updated successfully!");
        }
        return new ResponseEntity<>("Review not found!", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable Long reviewId) {
        boolean isDeleted = reviewService.deleteReview(reviewId);
        if (isDeleted) {
            return ResponseEntity.ok("Review deleted successfully!");
        }
        return new ResponseEntity<>("Review not found!", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/averageRating")
    public Double getAverageRating(@RequestParam Long companyId) {
        List<Review> reviewList = reviewService.getAllReviews(companyId);
        return reviewList.stream()
                .mapToDouble(Review::getRating)
                .average()
                .orElse(0.0);
    }
}
