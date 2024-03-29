package com.t2m.g2nee.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TokenResponseDto {
    private Access access;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Access {

        private Token token;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Token {

        private String id;
    }


}
