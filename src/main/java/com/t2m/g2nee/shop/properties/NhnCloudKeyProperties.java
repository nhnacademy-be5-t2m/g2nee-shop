package com.t2m.g2nee.shop.properties;

import com.t2m.g2nee.shop.dto.KeyResponseDto;
import java.util.List;
import java.util.Objects;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@ConfigurationProperties(prefix = "nhncloud.key")
@Data
public class NhnCloudKeyProperties {

    private String url;
    private String path;
    private String appKey;
    private String dbUrlKeyId;
    private String dbUsernameKeyId;
    private String dbPasswordKeyId;
    private String tossKeyId;

    @ConstructorBinding
    public NhnCloudKeyProperties(String url, String path, String appKey, String dbUrlKeyId, String dbUsernameKeyId,
                                 String dbPasswordKeyId, String tossKeyId) {
        this.url = url;
        this.path = path;
        this.appKey = appKey;
        this.dbUrlKeyId = dbUrlKeyId;
        this.dbUsernameKeyId = dbUsernameKeyId;
        this.dbPasswordKeyId = dbPasswordKeyId;
        this.tossKeyId = tossKeyId;
    }

    /**
     * nhncloud Api를 이용해서 properties를 가져오는 메서드
     *
     * @param keyId nhncloud 기밀데이터를 불러오기 위한 keyId 값
     * @return 기밀데이터의 string value 값
     **/
    public String getProperties(String keyId) {

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        String nhnUrl = getUrl() + getPath() + getAppKey();

        HttpEntity<String> requestEntity = new HttpEntity<>(httpHeaders);
        ResponseEntity<KeyResponseDto> exchange = restTemplate.exchange(nhnUrl + keyId,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<>() {
                });

        return Objects.requireNonNull(exchange.getBody()).getBody().getSecret().getValue();

    }
}
