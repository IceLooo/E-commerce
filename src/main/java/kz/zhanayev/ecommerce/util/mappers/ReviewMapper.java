package kz.zhanayev.ecommerce.util.mappers;

import kz.zhanayev.ecommerce.dto.ReviewDTO;
import kz.zhanayev.ecommerce.models.Product;
import kz.zhanayev.ecommerce.models.Review;
import kz.zhanayev.ecommerce.models.User;

public class ReviewMapper {
    public static ReviewDTO toDTO(Review review) {
        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setId(review.getId());
        reviewDTO.setProductId(review.getProduct().getId());
        reviewDTO.setUserId(review.getUser().getId());
        reviewDTO.setRating(review.getRating());
        reviewDTO.setComment(review.getComment());
        reviewDTO.setUserName(review.getUser().getFirstName() + " " + review.getUser().getLastName());
        reviewDTO.setCreatedAt(review.getCreatedAt());
        return reviewDTO;
    }

    public static Review toEntity(ReviewDTO reviewDTO) {
        Review review = new Review();
        review.setId(reviewDTO.getId());
        review.setRating(reviewDTO.getRating());
        review.setComment(reviewDTO.getComment());

        Product product = new Product();
        product.setId(reviewDTO.getProductId());
        review.setProduct(product);

        User user = new User();
        user.setId(reviewDTO.getUserId());
        review.setUser(user);

        return review;
    }
}
