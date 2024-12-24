package kz.zhanayev.ecommerce.repositories;

import kz.zhanayev.ecommerce.models.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
