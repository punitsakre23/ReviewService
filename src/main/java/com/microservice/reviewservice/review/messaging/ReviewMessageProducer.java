package com.microservice.reviewservice.review.messaging;

import com.microservice.reviewservice.review.Review;
import com.microservice.reviewservice.review.dto.ReviewMessage;
import com.microservice.reviewservice.review.mapper.ReviewMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class ReviewMessageProducer {

    private final RabbitTemplate rabbitTemplate;
    private final ReviewMapper reviewMapper;

    public ReviewMessageProducer(RabbitTemplate rabbitTemplate, ReviewMapper reviewMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.reviewMapper = reviewMapper;
    }

    public void sendMessage(Review review) {
        ReviewMessage reviewMessage = reviewMapper.mapToMessage(review);
        rabbitTemplate.convertAndSend("companyRatingQueue", reviewMessage);
    }
}
