package com.t2m.g2nee.shop.nhncloudStorageTest;

import static com.t2m.g2nee.shop.nhnstorage.AuthService.TokenRequest;
import static com.t2m.g2nee.shop.nhnstorage.AuthService.TokenRequest.Auth;
import static com.t2m.g2nee.shop.nhnstorage.AuthService.TokenRequest.PasswordCredentials;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.t2m.g2nee.shop.dto.TokenResponseDto;
import com.t2m.g2nee.shop.nhnstorage.AuthService;
import com.t2m.g2nee.shop.properties.NhnCloudTokenProperties;
import java.util.Objects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    private RestTemplate restTemplate;
    @Mock
    private NhnCloudTokenProperties properties;
    @Mock
    private TokenRequest tokenRequest;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        properties = new NhnCloudTokenProperties("https://api-identity-infrastructure.nhncloudservice.com/v2.0/"
                , "fcb81f74e379456b8ca0e091d351a7af"
                , "aokagami03@gmail.com"
                , "g2nee");

        PasswordCredentials passwordCredentials = new AuthService.TokenRequest.PasswordCredentials();
        passwordCredentials.setUsername(properties.getUsername());
        passwordCredentials.setPassword(properties.getPassword());

        Auth auth = new Auth();
        auth.setTenantId(properties.getTenantId());
        auth.setPasswordCredentials(passwordCredentials);

        tokenRequest = new AuthService.TokenRequest();
        tokenRequest.setAuth(auth);

        restTemplate = new RestTemplate();
    }

    @Test
    @DisplayName("토큰Id 생성 테스트")
    void requestToken() {

        // given
        String url = properties.getUrl() + "tokens";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content_Type", "application/json");

        System.out.println(tokenRequest);
        HttpEntity<TokenRequest> httpEntity = new HttpEntity<>(this.tokenRequest, headers);

        // when
        ResponseEntity<TokenResponseDto> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.POST,
                httpEntity,
                TokenResponseDto.class);

        // then
        String tokenId = Objects.requireNonNull(responseEntity.getBody()).getAccess().getToken().getId();
        assertNotNull(tokenId);
    }
}

