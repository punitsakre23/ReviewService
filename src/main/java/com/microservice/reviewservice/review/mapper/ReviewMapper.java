package com.microservice.reviewservice.review.mapper;

import com.microservice.reviewservice.review.Review;
import com.microservice.reviewservice.review.dto.ReviewMessage;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    ReviewMessage mapToMessage(Review review);
}
