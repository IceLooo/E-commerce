package kz.zhanayev.ecommerce.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CartItemDTO {
    private Long id;
    private Long productId;
    private String productName;
    private int quantity;
    private BigDecimal price;
}