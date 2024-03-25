package com.t2m.g2nee.shop.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConfigurationProperties(prefix = "nhncloud")
@Data
public class NhnCloudKey {

    private String endpoint;
    private String path;
    private String appKey;
    private String driverClassNameKeyId;
    private String urlKeyId;
    private String usernameKeyId;
    private String passwordKeyId;

    @ConstructorBinding
    public NhnCloudKey(String endpoint, String path, String appKey, String driverClassNameKeyId, String urlKeyId, String usernameKeyId, String passwordKeyId) {
        this.endpoint = endpoint;
        this.path = path;
        this.appKey = appKey;
        this.driverClassNameKeyId = driverClassNameKeyId;
        this.urlKeyId = urlKeyId;
        this.usernameKeyId = usernameKeyId;
        this.passwordKeyId = passwordKeyId;
    }
}
