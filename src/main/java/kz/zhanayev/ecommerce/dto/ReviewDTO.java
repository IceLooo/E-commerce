package kz.zhanayev.ecommerce.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReviewDTO {
    private Long id;
    private Long productId;
    private Long userId;
    private int rating;
    private String comment;
    private String userName; // Имя пользователя
    private LocalDateTime createdAt;
}
