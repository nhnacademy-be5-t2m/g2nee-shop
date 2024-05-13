package com.t2m.g2nee.shop.shoppingcart.controller;

import com.t2m.g2nee.shop.shoppingcart.dto.ShoppingCartDto;
import com.t2m.g2nee.shop.shoppingcart.service.ShoppingCartService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping("/book/{bookId}")
    public ResponseEntity<ShoppingCartDto.Response> getBookForCart(@PathVariable String bookId,
                                                                   @RequestParam("quantity") int quantity) {

        ShoppingCartDto.Response response = shoppingCartService.getBookForCart(bookId, quantity);

        return ResponseEntity.ok().body(response);
    }

    @PatchMapping("/migrate/member/{memberId}")
    public ResponseEntity migrateCartToDB(@PathVariable Long memberId,
                                          @RequestBody List<ShoppingCartDto.Request> requestList) {

        shoppingCartService.migrateCartToDB(memberId, requestList);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
