package kz.zhanayev.ecommerce.services;

import kz.zhanayev.ecommerce.dto.CartDTO;
import kz.zhanayev.ecommerce.dto.CartItemDTO;

public interface CartService {

     CartDTO getCartByUserId(Long userId);
     CartDTO addItemToCart(Long userId, CartItemDTO cartItemDTO);
     CartDTO removeItemFromCart(Long userId, Long itemId);
     CartDTO clearCart(Long userId);
}
