package com.t2m.g2nee.shop.shoppingcart.controller;

import com.t2m.g2nee.shop.shoppingcart.dto.ShoppingCartDto;
import com.t2m.g2nee.shop.shoppingcart.service.ShoppingCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * 장바구니 controller 클래스
 * @author : 신동민
 * @since : 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/shop/carts")
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    @GetMapping("/book/{bookId}")
    public ResponseEntity<ShoppingCartDto.Response> getBookForCart(@PathVariable String bookId,
                                                                   @RequestParam("quantity")int quantity){

        ShoppingCartDto.Response response = shoppingCartService.getBookForCart(bookId, quantity);

        return ResponseEntity.ok().body(response);
    }

}
