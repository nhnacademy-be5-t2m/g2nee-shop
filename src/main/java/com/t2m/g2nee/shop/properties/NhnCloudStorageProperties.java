package com.t2m.g2nee.shop.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConfigurationProperties(prefix = "nhncloud.storage")
@Data
public class NhnCloudStorageProperties {

    private String storageUrl;
    private String auth;
    private String containerName;

    @ConstructorBinding
    public NhnCloudStorageProperties(String storageUrl, String auth, String containerName) {
        this.storageUrl = storageUrl;
        this.auth = auth;
        this.containerName = containerName;
    }
}
