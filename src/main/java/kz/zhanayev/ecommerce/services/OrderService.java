package kz.zhanayev.ecommerce.services;


import kz.zhanayev.ecommerce.dto.OrderDTO;
import kz.zhanayev.ecommerce.models.Address;

import java.util.List;

public interface OrderService {

     List<OrderDTO> getAllOrders(String status);
     OrderDTO getOrderById(Long id);
     OrderDTO createOrderFromCart(Long userId, Address deliveryAddress);
     OrderDTO updateOrderStatus(Long id, String status);
     void deleteOrder(Long id, Long userId);
     List<OrderDTO> getAllOrdersByUserId(Long userId);
}
