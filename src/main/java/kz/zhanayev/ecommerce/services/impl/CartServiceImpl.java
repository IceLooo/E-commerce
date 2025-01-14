package kz.zhanayev.ecommerce.services.impl;

import kz.zhanayev.ecommerce.dto.CartDTO;
import kz.zhanayev.ecommerce.dto.CartItemDTO;
import kz.zhanayev.ecommerce.exceptions.NotFoundException;
import kz.zhanayev.ecommerce.models.Cart;
import kz.zhanayev.ecommerce.models.CartItem;
import kz.zhanayev.ecommerce.models.Product;
import kz.zhanayev.ecommerce.models.User;
import kz.zhanayev.ecommerce.repositories.CartRepository;
import kz.zhanayev.ecommerce.repositories.ProductRepository;
import kz.zhanayev.ecommerce.services.CartService;
import kz.zhanayev.ecommerce.util.mappers.CartMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public CartServiceImpl(CartRepository cartRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    @Override
    public CartDTO getCartByUserId(Long userId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException("Корзина не найдена по идентификатору пользователя: " + userId));
        return CartMapper.toDTO(cart);
    }

    @Override
    public CartDTO addItemToCart(Long userId, CartItemDTO cartItemDTO) {
        // Ищем корзину пользователя или создаём новую
        Cart cart = cartRepository.findByUserId(userId).orElseGet(() -> {
            Cart newCart = new Cart();
            User user = new User();
            user.setId(userId);
            newCart.setUser(user);
            newCart.setTotalPrice(BigDecimal.ZERO);
            return cartRepository.save(newCart);
        });

        // Проверяем, существует ли товар в корзине
        boolean itemExists = cart.getCartItems().stream()
                .anyMatch(item -> item.getProduct().getId().equals(cartItemDTO.getProductId()));

        if (itemExists) {
            // Обновляем количество, если товар уже есть
            cart.getCartItems().forEach(item -> {
                if (item.getProduct().getId().equals(cartItemDTO.getProductId())) {
                    item.setQuantity(item.getQuantity() + cartItemDTO.getQuantity());
                }
            });
        } else {
            // Получаем продукт и добавляем новый элемент в корзину
            Product product = productRepository.findById(cartItemDTO.getProductId())
                    .orElseThrow(() -> new NotFoundException("Продукт не найден с идентификатором: " + cartItemDTO.getProductId()));

            CartItem cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setQuantity(cartItemDTO.getQuantity());
            cartItem.setPrice(cartItemDTO.getPrice());
            cartItem.setCart(cart); // Связываем элемент с корзиной
            cart.getCartItems().add(cartItem);
        }

        // Обновляем общую стоимость корзины
        updateTotalPrice(cart);
        return CartMapper.toDTO(cartRepository.save(cart));
    }

    @Override
    public CartDTO removeItemFromCart(Long userId, Long itemId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException("Корзина не найдена по идентификатору пользователя: " + userId));
        cart.getCartItems().removeIf(item -> item.getId().equals(itemId));
        updateTotalPrice(cart);
        return CartMapper.toDTO(cartRepository.save(cart));
    }

    @Override
    public CartDTO clearCart(Long userId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException("Корзина не найдена по идентификатору пользователя: " + userId));
        cart.getCartItems().clear();
        updateTotalPrice(cart);
        return CartMapper.toDTO(cartRepository.save(cart));
    }

    private void updateTotalPrice(Cart cart) {
        BigDecimal totalPrice = cart.getCartItems().stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        cart.setTotalPrice(totalPrice);
    }
}
