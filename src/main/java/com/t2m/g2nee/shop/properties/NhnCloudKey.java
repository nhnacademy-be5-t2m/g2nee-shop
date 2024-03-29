package com.t2m.g2nee.shop.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConfigurationProperties(prefix = "nhncloud")
@Data
public class NhnCloudKey {

    private String url;
    private String path;
    private String appKey;
    private String urlKeyId;
    private String usernameKeyId;
    private String passwordKeyId;

    @ConstructorBinding
    public NhnCloudKey(String url, String path, String appKey, String urlKeyId, String usernameKeyId, String passwordKeyId) {
        this.url = url;
        this.path = path;
        this.appKey = appKey;
        this.urlKeyId = urlKeyId;
        this.usernameKeyId = usernameKeyId;
        this.passwordKeyId = passwordKeyId;
    }
}
