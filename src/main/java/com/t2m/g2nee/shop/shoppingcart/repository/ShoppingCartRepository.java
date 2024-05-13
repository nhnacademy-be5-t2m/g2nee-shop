package com.t2m.g2nee.shop.shoppingcart.repository;

import com.t2m.g2nee.shop.shoppingcart.domain.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


/**
 * 장바구니 repository 클래스
 *
 * @author : 신동민
 * @since : 1.0
 */
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long>, ShoppingCartCustomRepository {

    @Modifying
    @Query("DELETE FROM ShoppingCart sc WHERE sc.member.customerId= :memberId")
    void deleteByMemberId(Long memberId);

}
