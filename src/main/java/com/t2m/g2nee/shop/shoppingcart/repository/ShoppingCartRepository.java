package com.t2m.g2nee.shop.shoppingcart.repository;

import com.t2m.g2nee.shop.shoppingcart.domain.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * 장바구니 repository 클래스
 *
 * @author : 신동민
 * @since : 1.0
 */
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long>, ShoppingCartCustomRepository {
}
