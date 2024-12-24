package kz.zhanayev.ecommerce.facade;

import kz.zhanayev.ecommerce.dto.CartDTO;
import kz.zhanayev.ecommerce.dto.CartItemDTO;
import kz.zhanayev.ecommerce.models.Cart;
import kz.zhanayev.ecommerce.models.CartItem;
import kz.zhanayev.ecommerce.models.Product;
import kz.zhanayev.ecommerce.models.User;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class CartFacade {

    public CartDTO toDTO(Cart cart) {
        CartDTO cartDTO = new CartDTO();
        cartDTO.setId(cart.getId());
        cartDTO.setTotalPrice(cart.getTotalPrice());
        cartDTO.setUserId(cart.getUser().getId());
        cartDTO.setCartItems(cart.getCartItems().stream().map(this::toItemDTO).collect(Collectors.toList()));
        return cartDTO;
    }

    public Cart toEntity(CartDTO cartDTO) {
        Cart cart = new Cart();
        cart.setId(cartDTO.getId());
        cart.setTotalPrice(cartDTO.getTotalPrice());

        User user = new User();
        user.setId(cartDTO.getUserId()); // Используем сеттер
        cart.setUser(user);

        cart.setCartItems(cartDTO.getCartItems().stream().map(this::toCartItemEntity).collect(Collectors.toList()));
        return cart;
    }

    public CartItemDTO toItemDTO(CartItem cartItem) {
        CartItemDTO cartItemDTO = new CartItemDTO();
        cartItemDTO.setId(cartItem.getId());
        cartItemDTO.setProductId(cartItem.getProduct().getId());
        cartItemDTO.setProductName(cartItem.getProduct().getName());
        cartItemDTO.setQuantity(cartItem.getQuantity());
        cartItemDTO.setPrice(cartItem.getPrice());
        return cartItemDTO;
    }

    public CartItem toCartItemEntity(CartItemDTO cartItemDTO) {
        CartItem cartItem = new CartItem();
        cartItem.setId(cartItemDTO.getId());

        Product product = new Product();
        product.setId(cartItemDTO.getProductId()); // Используем сеттер
        cartItem.setProduct(product);

        cartItem.setQuantity(cartItemDTO.getQuantity());
        cartItem.setPrice(cartItemDTO.getPrice());
        return cartItem;
    }
}
