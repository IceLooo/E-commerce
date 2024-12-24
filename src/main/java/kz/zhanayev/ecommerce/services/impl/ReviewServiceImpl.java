package kz.zhanayev.ecommerce.services.impl;

import kz.zhanayev.ecommerce.dto.ReviewDTO;
import kz.zhanayev.ecommerce.exceptions.ResourceNotFoundException;
import kz.zhanayev.ecommerce.facade.ReviewFacade;
import kz.zhanayev.ecommerce.models.Product;
import kz.zhanayev.ecommerce.models.Review;
import kz.zhanayev.ecommerce.models.User;
import kz.zhanayev.ecommerce.repositories.ProductRepository;
import kz.zhanayev.ecommerce.repositories.ReviewRepository;
import kz.zhanayev.ecommerce.repositories.UserRepository;
import kz.zhanayev.ecommerce.services.ReviewService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final ReviewFacade reviewFacade;


    public ReviewServiceImpl(ReviewRepository reviewRepository, ProductRepository productRepository, UserRepository userRepository, ReviewFacade reviewFacade) {
        this.reviewRepository = reviewRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.reviewFacade = reviewFacade;
    }

    @Override
    public ReviewDTO addReview(Long productId, Long userId, ReviewDTO reviewDTO) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + productId));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));

        Review review = reviewFacade.dtoToReview(reviewDTO);
        review.setProduct(product);
        review.setUser(user);

        Review savedReview = reviewRepository.save(review);
        return reviewFacade.reviewToDTO(savedReview);
    }

    @Override
    public List<ReviewDTO> getReviewsByProductId(Long productId) {
        List<Review> reviews = reviewRepository.findByProductId(productId);
        return reviews.stream()
                .map(reviewFacade::reviewToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReviewDTO> getReviewsByUserId(Long userId) {
        List<Review> reviews = reviewRepository.findByUserId(userId);
        return reviews.stream()
                .map(reviewFacade::reviewToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found with ID: " + reviewId));
        reviewRepository.delete(review);
    }

}
