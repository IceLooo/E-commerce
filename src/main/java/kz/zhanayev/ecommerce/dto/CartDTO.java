package kz.zhanayev.ecommerce.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class CartDTO {
    private Long id;
    private Long userId;
    private List<CartItemDTO> cartItems;
    private BigDecimal totalPrice;
}
