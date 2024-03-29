package com.t2m.g2nee.shop.nhnstorage;

import com.t2m.g2nee.shop.dto.TokenResponseDto;
import com.t2m.g2nee.shop.properties.NhnCloudTokenProperties;
import java.util.Objects;
import lombok.Data;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * objectStorage 토큰발급 로직이 있는 Service 입니다.
 *
 * @author : 신동민
 * @since : 1.0
 */
@Data
@Service
public class AuthService {

    private NhnCloudTokenProperties properties;
    private TokenRequest tokenRequest;
    private RestTemplate restTemplate;

    public AuthService(NhnCloudTokenProperties properties) {
        // 요청 본문 생성
        this.tokenRequest = new TokenRequest();
        this.tokenRequest.getAuth().setTenantId(properties.getTenantId());
        this.tokenRequest.getAuth().getPasswordCredentials().setUsername(properties.getUsername());
        this.tokenRequest.getAuth().getPasswordCredentials().setPassword(properties.getPassword());
        this.properties = properties;
        this.restTemplate = new RestTemplate();
    }

    /**
     * nhncloud api에 접근하기 위한 token을 생성하는 메서드
     *
     * @return tokenId String 값
     */
    public String requestToken() {
        String identityUrl = properties.getUrl() + "/tokens";

        // 헤더 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        HttpEntity<TokenRequest> httpEntity = new HttpEntity<TokenRequest>(this.tokenRequest, headers);
        // 토큰 요청
        ResponseEntity<TokenResponseDto> response =
                this.restTemplate.exchange(identityUrl, HttpMethod.POST, httpEntity, TokenResponseDto.class);


        return Objects.requireNonNull(response.getBody()).getAccess().getToken().getId();
    }

    // Inner class for the request body
    @Data
    public static class TokenRequest {

        private Auth auth = new Auth();

        @Data
        public static class Auth {
            private String tenantId;
            private PasswordCredentials passwordCredentials = new PasswordCredentials();
        }

        @Data
        public static class PasswordCredentials {
            private String username;
            private String password;
        }
    }
}
