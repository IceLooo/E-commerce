package kz.zhanayev.ecommerce.dto;

import kz.zhanayev.ecommerce.models.enums.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class OrderDTO {
    private Long id;
//    private String userEmail;
    private Long userId;
    private List<OrderItemDTO> orderItems;
    private BigDecimal totalPrice;
    private AddressDTO address;
    private LocalDateTime createdAt;
    private OrderStatus status;
}
