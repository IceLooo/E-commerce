package kz.zhanayev.ecommerce.services.impl;

import kz.zhanayev.ecommerce.dto.ReviewDTO;
import kz.zhanayev.ecommerce.exceptions.NotFoundException;
import kz.zhanayev.ecommerce.models.Product;
import kz.zhanayev.ecommerce.models.Review;
import kz.zhanayev.ecommerce.models.User;
import kz.zhanayev.ecommerce.repositories.ProductRepository;
import kz.zhanayev.ecommerce.repositories.ReviewRepository;
import kz.zhanayev.ecommerce.repositories.UserRepository;
import kz.zhanayev.ecommerce.services.ReviewService;
import kz.zhanayev.ecommerce.util.mappers.ReviewMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository, ProductRepository productRepository, UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ReviewDTO addReview(Long productId, Long userId, ReviewDTO reviewDTO) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Продукт не найден с идентификатором: " + productId));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с идентификатором не найден: " + userId));

        Review review = ReviewMapper.toEntity(reviewDTO);
        review.setProduct(product);
        review.setUser(user);

        Review savedReview = reviewRepository.save(review);
        return ReviewMapper.toDTO(savedReview);
    }

    @Override
    public List<ReviewDTO> getReviewsByProductId(Long productId) {
        List<Review> reviews = reviewRepository.findByProductId(productId);
        return reviews.stream()
                .map(ReviewMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReviewDTO> getReviewsByUserId(Long userId) {
        List<Review> reviews = reviewRepository.findByUserId(userId);
        return reviews.stream()
                .map(ReviewMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new NotFoundException("Отзыв с идентификатором не найден: " + reviewId));
        reviewRepository.delete(review);
    }
}
