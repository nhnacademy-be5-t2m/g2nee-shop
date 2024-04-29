package com.t2m.g2nee.shop.config;

import com.t2m.g2nee.shop.dto.KeyResponseDto;
import com.t2m.g2nee.shop.properties.NhnCloudKeyProperties;
import com.t2m.g2nee.shop.properties.TossPaymentProperties;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@Configuration
public class TossConfig {

    private final NhnCloudKeyProperties nhnCloudKeyProperties;
    private final String url;

    public TossConfig(NhnCloudKeyProperties nhnCloudKeyProperties) {
        this.nhnCloudKeyProperties = nhnCloudKeyProperties;
        this.url = nhnCloudKeyProperties.getUrl() + nhnCloudKeyProperties.getPath() + nhnCloudKeyProperties.getAppKey();
    }

    @Bean
    public String getTossSecretKey() {
        TossPaymentProperties properties = getTossPaymentProperties();
        Base64.Encoder encoder = Base64.getEncoder();
        byte[] encodedBytes = encoder.encode(properties.getSecretKey().getBytes(StandardCharsets.UTF_8));

        return new String(encodedBytes);
    }

    /**
     * nhncloud Api를 이용해서 TossPaymentProperties를 가져오는 메서드
     *
     * @return TossPayment의 설정 값이 담긴 TossPaymentProperties를 객체
     **/
    public TossPaymentProperties getTossPaymentProperties() {
        return new TossPaymentProperties(getProperties(nhnCloudKeyProperties.getTossKeyId()));

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

        HttpEntity<String> requestEntity = new HttpEntity<>(httpHeaders);
        ResponseEntity<KeyResponseDto> exchange = restTemplate.exchange(url + keyId,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<>() {
                });

        return Objects.requireNonNull(exchange.getBody()).getBody().getSecret().getValue();

    }
}
