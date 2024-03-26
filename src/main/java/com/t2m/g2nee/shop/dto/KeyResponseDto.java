package com.t2m.g2nee.shop.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class KeyResponseDto {
    private Header header;
    private Body body;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Header {
        private int resultCode;
        private String resultMessage;
        private boolean isSuccessful;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Body {

        private Secret secret;
    }
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Secret {

        private String value;
    }

}
