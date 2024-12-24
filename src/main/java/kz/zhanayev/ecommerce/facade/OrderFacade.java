package kz.zhanayev.ecommerce.facade;

import kz.zhanayev.ecommerce.dto.OrderDTO;
import kz.zhanayev.ecommerce.dto.OrderItemDTO;
import kz.zhanayev.ecommerce.models.Order;
import kz.zhanayev.ecommerce.models.OrderItem;
import org.springframework.stereotype.Component;

@Component
public class OrderFacade {

    private final AddressFacade addressFacade;

    public OrderFacade(AddressFacade addressFacade) {
        this.addressFacade = addressFacade;
    }

    public OrderDTO orderToOrderDTO(Order order) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(order.getId());
        orderDTO.setUserId(order.getUser().getId());
        orderDTO.setTotalPrice(order.getTotalPrice());
        orderDTO.setStatus(order.getStatus());
        orderDTO.setAddress(order.getAddress() != null ? addressFacade.entityToDTO(order.getAddress()) : null);
        orderDTO.setCreatedAt(order.getCreatedAt());
        orderDTO.setOrderItems(order.getOrderItems().stream().map(this::orderItemToOrderItemDTO).toList());
        return orderDTO;
    }

    private OrderItemDTO orderItemToOrderItemDTO(OrderItem orderItem) {
        OrderItemDTO orderItemDTO = new OrderItemDTO();
        orderItemDTO.setId(orderItem.getId());
        orderItemDTO.setProductId(orderItem.getProduct().getId());
        orderItemDTO.setProductName(orderItem.getProduct().getName());
        orderItemDTO.setQuantity(orderItem.getQuantity());
        orderItemDTO.setPrice(orderItem.getPrice());
        return orderItemDTO;
    }
}
