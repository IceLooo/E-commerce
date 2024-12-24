package kz.zhanayev.ecommerce.facade;

import kz.zhanayev.ecommerce.dto.ReviewDTO;
import kz.zhanayev.ecommerce.models.Review;
import org.springframework.stereotype.Component;

@Component
public class ReviewFacade {
    public ReviewDTO reviewToDTO(Review review) {
        ReviewDTO dto = new ReviewDTO();
        dto.setId(review.getId());
        dto.setProductId(review.getProduct().getId());
        dto.setUserId(review.getUser().getId());
        dto.setRating(review.getRating());
        dto.setComment(review.getComment());
        dto.setUserName(review.getUser().getFirstName() + " " + review.getUser().getLastName());
        dto.setCreatedAt(review.getCreatedAt());
        return dto;
    }

    public Review dtoToReview(ReviewDTO reviewDTO) {
        Review review = new Review();
        review.setId(reviewDTO.getId());
        review.setRating(reviewDTO.getRating());
        review.setComment(reviewDTO.getComment());
        return review;
    }
}
