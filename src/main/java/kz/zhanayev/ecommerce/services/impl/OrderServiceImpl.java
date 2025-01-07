package kz.zhanayev.ecommerce.services.impl;

import kz.zhanayev.ecommerce.dto.CartDTO;
import kz.zhanayev.ecommerce.dto.OrderDTO;
import kz.zhanayev.ecommerce.exceptions.NotFoundException;
import kz.zhanayev.ecommerce.models.*;
import kz.zhanayev.ecommerce.models.enums.OrderStatus;
import kz.zhanayev.ecommerce.repositories.AddressRepository;
import kz.zhanayev.ecommerce.repositories.OrderRepository;
import kz.zhanayev.ecommerce.repositories.ProductRepository;
import kz.zhanayev.ecommerce.services.CartService;
import kz.zhanayev.ecommerce.services.OrderService;
import kz.zhanayev.ecommerce.util.mappers.CartMapper;
import kz.zhanayev.ecommerce.util.mappers.OrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CartService cartService;
    private final AddressRepository addressRepository;
    private final ProductRepository productRepository;

    public OrderServiceImpl(OrderRepository orderRepository,
                            CartService cartService,
                            AddressRepository addressRepository,
                            ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.cartService = cartService;
        this.addressRepository = addressRepository;
        this.productRepository = productRepository;
    }

    @Override
    public List<OrderDTO> getAllOrders(String status) {
        List<Order> orders;
        if (status != null) {
            try {
                OrderStatus orderStatus = OrderStatus.valueOf(status.toUpperCase());
                orders = orderRepository.findByStatus(orderStatus);
            } catch (IllegalArgumentException e) {
                throw new NotFoundException("Недопустимый статус: " + status);
            }
        } else {
            orders = orderRepository.findAll();
        }
        return orders.stream()
                .map(OrderMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderDTO> getAllOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId).stream()
                .map(OrderMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public OrderDTO getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Заказ с идентификатором не найден: " + id));
        return OrderMapper.toDTO(order);
    }

    @Override
    public OrderDTO createOrderFromCart(Long userId, Address deliveryAddress) {
        CartDTO cartDTO = cartService.getCartByUserId(userId);
        Cart cart = CartMapper.toEntity(cartDTO);

        if (cart.getCartItems().isEmpty()) {
            throw new NotFoundException("Не удается создать заказ из пустой корзины");
        }

        deliveryAddress.setUser(cart.getUser()); // Привязка пользователя к адресу
        Address savedAddress = addressRepository.save(deliveryAddress);

        Order order = new Order();
        order.setUser(cart.getUser());
        order.setAddress(savedAddress);
        order.setStatus(OrderStatus.PENDING);

        List<OrderItem> orderItems = cart.getCartItems().stream().map(cartItem -> {
            Product product = productRepository.findById(cartItem.getProduct().getId())
                    .orElseThrow(() -> new NotFoundException("Продукт не найден с идентификатором: " + cartItem.getProduct().getId()));
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product); // Здесь явно загружаем продукт
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getPrice());
            return orderItem;
        }).collect(Collectors.toList());

        order.setOrderItems(orderItems);
        updateTotalPrice(order);

        cartService.clearCart(userId);
        Order savedOrder = orderRepository.save(order);
        return OrderMapper.toDTO(savedOrder);
    }

    @Override
    public OrderDTO updateOrderStatus(Long id, String status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Заказ с идентификатором не найден: " + id));
        try {
            OrderStatus newStatus = OrderStatus.valueOf(status.toUpperCase());
            order.setStatus(newStatus);
            return OrderMapper.toDTO(orderRepository.save(order));
        } catch (IllegalArgumentException e) {
            throw new NotFoundException("Недопустимый статус: " + status);
        }
    }

    private void updateTotalPrice(Order order) {
        BigDecimal totalPrice = order.getOrderItems().stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        order.setTotalPrice(totalPrice);
    }

    @Override
    public void deleteOrder(Long id, Long userId) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Заказ с идентификатором не найден: " + id));

        if (!order.getUser().getId().equals(userId)) {
            throw new NotFoundException("Неавторизованный для удаления этого заказа");
        }
        orderRepository.delete(order);
    }
}
