package com.t2m.g2nee.shop.properties;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DataSourceProperties {

    private String url;
    private String username;
    private String password;
}
