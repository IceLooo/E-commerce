package kz.zhanayev.ecommerce.repositories;

import kz.zhanayev.ecommerce.models.Order;
import kz.zhanayev.ecommerce.models.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId);
    List<Order> findByStatus(OrderStatus status);
}
