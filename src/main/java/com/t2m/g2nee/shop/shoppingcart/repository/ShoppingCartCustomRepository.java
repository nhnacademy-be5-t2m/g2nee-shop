package com.t2m.g2nee.shop.shoppingcart.repository;

import com.t2m.g2nee.shop.shoppingcart.dto.ShoppingCartDto;

public interface ShoppingCartCustomRepository {

    ShoppingCartDto.Response getBookForCart(Long bookId);
}
