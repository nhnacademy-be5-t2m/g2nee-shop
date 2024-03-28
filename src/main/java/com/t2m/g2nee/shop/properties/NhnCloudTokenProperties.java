package com.t2m.g2nee.shop.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConfigurationProperties(prefix = "nhncloud.token")
@Data
public class NhnCloudTokenProperties {

    private String url;
    private String tenantId;
    private String username;
    private String password;

    @ConstructorBinding
    public NhnCloudTokenProperties(String url, String tenantId, String username, String password) {
        this.url = url;
        this.tenantId = tenantId;
        this.username = username;
        this.password = password;
    }
}
