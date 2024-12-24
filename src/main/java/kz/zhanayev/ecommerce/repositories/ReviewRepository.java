package kz.zhanayev.ecommerce.repositories;

import kz.zhanayev.ecommerce.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByProductId(Long productId);

    List<Review> findByUserId(Long userId);
}
