package kz.zhanayev.ecommerce.services.impl;

import kz.zhanayev.ecommerce.dto.CartDTO;
import kz.zhanayev.ecommerce.dto.OrderDTO;
import kz.zhanayev.ecommerce.exceptions.EmptyCartException;
import kz.zhanayev.ecommerce.exceptions.InvalidOrderStatusException;
import kz.zhanayev.ecommerce.exceptions.ResourceNotFoundException;
import kz.zhanayev.ecommerce.facade.CartFacade;
import kz.zhanayev.ecommerce.facade.OrderFacade;
import kz.zhanayev.ecommerce.models.*;
import kz.zhanayev.ecommerce.models.enums.OrderStatus;
import kz.zhanayev.ecommerce.repositories.AddressRepository;
import kz.zhanayev.ecommerce.repositories.OrderRepository;
import kz.zhanayev.ecommerce.repositories.ProductRepository;
import kz.zhanayev.ecommerce.services.CartService;
import kz.zhanayev.ecommerce.services.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderFacade orderFacade;
    private final CartService cartService;
    private final CartFacade cartFacade;
    private final AddressRepository addressRepository;
    private final ProductRepository productRepository;

    public OrderServiceImpl(OrderRepository orderRepository,
                            OrderFacade orderFacade,
                            CartService cartService, CartFacade cartFacade, AddressRepository addressRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.orderFacade = orderFacade;
        this.cartService = cartService;
        this.cartFacade = cartFacade;
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
                throw new RuntimeException("Invalid status: " + status);
            }
        } else {
            orders = orderRepository.findAll();
        }
        return orders.stream()
                .map(orderFacade::orderToOrderDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderDTO> getAllOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId).stream()
                .map(orderFacade::orderToOrderDTO)
                .collect(Collectors.toList());
    }

    @Override
    public OrderDTO getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
        return orderFacade.orderToOrderDTO(order);
    }

    @Override
    public OrderDTO createOrderFromCart(Long userId, Address deliveryAddress) {
        CartDTO cartDTO = cartService.getCartByUserId(userId);
        Cart cart = cartFacade.toEntity(cartDTO);

        if (cart.getCartItems().isEmpty()) {
            throw new EmptyCartException("Cannot create order from an empty cart.");
        }

        deliveryAddress.setUser(cart.getUser()); // Привязка пользователя к адресу
        Address savedAddress = addressRepository.save(deliveryAddress);

        Order order = new Order();
        order.setUser(cart.getUser());
        order.setAddress(savedAddress);
        order.setStatus(OrderStatus.PENDING);

        List<OrderItem> orderItems = cart.getCartItems().stream().map(cartItem -> {
            Product product = productRepository.findById(cartItem.getProduct().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + cartItem.getProduct().getId()));
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
        return orderFacade.orderToOrderDTO(savedOrder);
    }

    @Override
    public OrderDTO updateOrderStatus(Long id, String status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
        try {
            OrderStatus newStatus = OrderStatus.valueOf(status.toUpperCase());
            order.setStatus(newStatus);
            return orderFacade.orderToOrderDTO(orderRepository.save(order));
        } catch (IllegalArgumentException e) {
            throw new InvalidOrderStatusException("Invalid status: " + status, e);
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
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));

        if (!order.getUser().getId().equals(userId)) {
            throw new RuntimeException("Unauthorized to delete this order");
        }
        orderRepository.delete(order);
    }
}
