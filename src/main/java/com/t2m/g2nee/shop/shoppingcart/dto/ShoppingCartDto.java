package com.t2m.g2nee.shop.shoppingcart.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 장바구니 dto 클래스
 *
 * @author : 신동민
 * @since : 1.0
 */
public class ShoppingCartDto {

    private ShoppingCartDto() {
    }

    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Request {

        private int quantity;
        private Long bookId;
        private Long memberId;
    }

    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Response {

        private Long shoppingCartId;
        private Long memberId;
        private Long bookId;
        private int quantity;
        private String title;
        private String imageUrl;
        private int price;
        private int bookQuantity;
    }
}
