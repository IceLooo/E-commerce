package kz.zhanayev.ecommerce.util.mappers;

import kz.zhanayev.ecommerce.dto.OrderDTO;
import kz.zhanayev.ecommerce.dto.OrderItemDTO;
import kz.zhanayev.ecommerce.models.*;

import java.util.stream.Collectors;

public class OrderMapper {
    public static OrderDTO toDTO(Order order) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(order.getId());
        orderDTO.setUserId(order.getUser().getId());
        orderDTO.setTotalPrice(order.getTotalPrice());
        orderDTO.setStatus(order.getStatus());
        orderDTO.setCreatedAt(order.getCreatedAt());

        if (order.getAddress() != null) {
            orderDTO.setAddressId(order.getAddress().getId());
            // Если нужно включить AddressDTO, используйте AddressMapper
            orderDTO.setAddress(AddressMapper.toDTO(order.getAddress()));
        }

        // Преобразование списка OrderItem в OrderItemDTO
        orderDTO.setOrderItems(order.getOrderItems().stream()
                .map(OrderMapper::toItemDTO)
                .collect(Collectors.toList()));

        return orderDTO;
    }

    public static Order toEntity(OrderDTO orderDTO) {
        Order order = new Order();
        order.setId(orderDTO.getId());
        order.setTotalPrice(orderDTO.getTotalPrice());
        order.setStatus(orderDTO.getStatus());
        order.setCreatedAt(orderDTO.getCreatedAt());

        User user = new User();
        user.setId(orderDTO.getUserId()); // Используем userId
        order.setUser(user);

        Address address = new Address();
        address.setId(orderDTO.getAddressId());
        order.setAddress(address);

        // Преобразование списка OrderItemDTO в OrderItem
        order.setOrderItems(orderDTO.getOrderItems().stream()
                .map(OrderMapper::toOrderItemEntity)
                .collect(Collectors.toList()));

        return order;
    }

    public static OrderItemDTO toItemDTO(OrderItem orderItem) {
        OrderItemDTO orderItemDTO = new OrderItemDTO();
        orderItemDTO.setId(orderItem.getId());
        orderItemDTO.setProductId(orderItem.getProduct().getId());
        orderItemDTO.setProductName(orderItem.getProduct().getName());
        orderItemDTO.setQuantity(orderItem.getQuantity());
        orderItemDTO.setPrice(orderItem.getPrice());
        return orderItemDTO;
    }

    public static OrderItem toOrderItemEntity(OrderItemDTO orderItemDTO) {
        OrderItem orderItem = new OrderItem();
        orderItem.setId(orderItemDTO.getId());
        orderItem.setQuantity(orderItemDTO.getQuantity());
        orderItem.setPrice(orderItemDTO.getPrice());

        Product product = new Product();
        product.setId(orderItemDTO.getProductId());
        orderItem.setProduct(product);

        return orderItem;
    }
}
