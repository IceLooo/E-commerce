package kz.zhanayev.ecommerce.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReviewDTO {

    @Schema(description = "Уникальный идентификатор отзыва", example = "1")
    private Long id;

    @Schema(description = "Идентификатор товара, к которому относится отзыв", example = "101")
    private Long productId;

    @Schema(description = "Идентификатор пользователя, оставившего отзыв", example = "1001")
    private Long userId;

    @Schema(description = "Оценка товара в виде числа от 1 до 5", example = "4")
    private int rating;

    @Schema(description = "Комментарий пользователя", example = "Отличный товар, рекомендую!")
    private String comment;

    @Schema(description = "Имя пользователя, оставившего отзыв", example = "Иван Иванов")
    private String userName;

    @Schema(description = "Дата и время создания отзыва", example = "2025-01-06T14:30:00")
    private LocalDateTime createdAt;
}
