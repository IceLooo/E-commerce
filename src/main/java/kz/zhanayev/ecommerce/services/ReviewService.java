package kz.zhanayev.ecommerce.services;
import kz.zhanayev.ecommerce.dto.ReviewDTO;

import java.util.List;

public interface ReviewService {

     ReviewDTO addReview(Long productId, Long userId, ReviewDTO reviewDTO);
     List<ReviewDTO> getReviewsByProductId(Long productId);
     List<ReviewDTO> getReviewsByUserId(Long userId);
     void deleteReview(Long reviewId);
}
