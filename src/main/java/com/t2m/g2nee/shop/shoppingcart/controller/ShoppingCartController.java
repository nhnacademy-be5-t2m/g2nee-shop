package com.t2m.g2nee.shop.shoppingcart.controller;

import com.t2m.g2nee.shop.shoppingcart.dto.ShoppingCartDto;
import com.t2m.g2nee.shop.shoppingcart.service.ShoppingCartService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 장바구니 controller 클래스
 *
 * @author : 신동민
 * @since : 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/shop/carts")
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    @PostMapping
    public ResponseEntity<ShoppingCartDto.Response> putBook(@RequestBody ShoppingCartDto.Request request) {

        ShoppingCartDto.Response response = shoppingCartService.putBookInCart(request);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/member/{memberId}")
    public ResponseEntity<List<ShoppingCartDto.Response>> getCartInBook(@PathVariable("memberId") Long memberId) {


        List<ShoppingCartDto.Response> responses = shoppingCartService.getShoppingCart(memberId);

        return ResponseEntity.ok().body(responses);
    }

    @DeleteMapping("/{cartId}")
    public ResponseEntity<List<ShoppingCartDto.Response>> deleteCartInBook(
            @PathVariable("cartId") Long shoppingCartId) {


        shoppingCartService.deleteBookInCart(shoppingCartId);

        return ResponseEntity.noContent().build();
    }

}
