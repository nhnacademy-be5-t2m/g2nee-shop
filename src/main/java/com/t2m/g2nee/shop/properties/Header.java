package com.t2m.g2nee.shop.properties;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Header {
    private int resultCode;
    private String resultMessage;
    private boolean isSuccessful;
}
