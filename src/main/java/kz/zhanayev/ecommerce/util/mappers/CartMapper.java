package kz.zhanayev.ecommerce.util.mappers;

import kz.zhanayev.ecommerce.dto.CartDTO;
import kz.zhanayev.ecommerce.dto.CartItemDTO;
import kz.zhanayev.ecommerce.models.Cart;
import kz.zhanayev.ecommerce.models.CartItem;
import kz.zhanayev.ecommerce.models.Product;
import kz.zhanayev.ecommerce.models.User;

import java.util.List;
import java.util.stream.Collectors;

public class CartMapper {
    public static CartDTO toDTO(Cart cart) {
        CartDTO cartDTO = new CartDTO();
        cartDTO.setId(cart.getId());
        cartDTO.setUserId(cart.getUser().getId());
        cartDTO.setTotalPrice(cart.getTotalPrice());

        // Преобразование списка CartItem в CartItemDTO
        cartDTO.setCartItems(cart.getCartItems().stream()
                .map(CartMapper::toItemDTO)
                .collect(Collectors.toList()));

        return cartDTO;
    }

    public static Cart toEntity(CartDTO cartDTO) {
        Cart cart = new Cart();
        cart.setId(cartDTO.getId());
        cart.setTotalPrice(cartDTO.getTotalPrice());

        User user = new User();
        user.setId(cartDTO.getUserId()); // Используем сеттер
        cart.setUser(user);

        // Преобразование списка CartItemDTO в CartItem
        cart.setCartItems(cartDTO.getCartItems().stream()
                .map(CartMapper::toCartItemEntity)
                .collect(Collectors.toList()));

        return cart;
    }

    public static CartItemDTO toItemDTO(CartItem cartItem) {
        CartItemDTO cartItemDTO = new CartItemDTO();
        cartItemDTO.setId(cartItem.getId());
        cartItemDTO.setProductId(cartItem.getProduct().getId());
        cartItemDTO.setProductName(cartItem.getProduct().getName());
        cartItemDTO.setQuantity(cartItem.getQuantity());
        cartItemDTO.setPrice(cartItem.getPrice());
        return cartItemDTO;
    }

    public static CartItem toCartItemEntity(CartItemDTO cartItemDTO) {
        CartItem cartItem = new CartItem();
        cartItem.setId(cartItemDTO.getId());

        Product product = new Product();
        product.setId(cartItemDTO.getProductId());
        cartItem.setProduct(product);

        cartItem.setQuantity(cartItemDTO.getQuantity());
        cartItem.setPrice(cartItemDTO.getPrice());
        return cartItem;
    }

    public static List<CartItemDTO> toItemDTOList(List<CartItem> cartItems) {
        return cartItems.stream()
                .map(CartMapper::toItemDTO)
                .collect(Collectors.toList());
    }
}
