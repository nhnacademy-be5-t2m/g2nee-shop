package com.t2m.g2nee.shop.shoppingcart.repository;

import com.t2m.g2nee.shop.shoppingcart.dto.ShoppingCartDto;
import java.util.List;

public interface ShoppingCartCustomRepository {

    List<ShoppingCartDto.Response> getShoppingCart(Long memberId);

    ShoppingCartDto.Response getCartBook(Long bookId);
}
