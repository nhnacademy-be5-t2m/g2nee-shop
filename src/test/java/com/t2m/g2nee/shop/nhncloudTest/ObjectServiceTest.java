package com.t2m.g2nee.shop.nhncloudTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.t2m.g2nee.shop.dto.TokenResponseDto;
import com.t2m.g2nee.shop.nhnstorage.AuthService;
import com.t2m.g2nee.shop.properties.NhnCloudStorageProperties;
import com.t2m.g2nee.shop.properties.NhnCloudTokenProperties;
import java.io.FileNotFoundException;
import java.util.Objects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpMessageConverterExtractor;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.RestTemplate;

@ExtendWith(MockitoExtension.class)
public class ObjectServiceTest {

    private RestTemplate restTemplate;

    @Mock
    private NhnCloudTokenProperties tokenProperties;
    @Mock
    private AuthService.TokenRequest tokenRequest;

    @Mock
    private NhnCloudStorageProperties storageProperties;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        tokenProperties = new NhnCloudTokenProperties("https://api-identity-infrastructure.nhncloudservice.com/v2.0/"
                , "fcb81f74e379456b8ca0e091d351a7af"
                , "aokagami03@gmail.com"
                , "g2nee");

        AuthService.TokenRequest.PasswordCredentials passwordCredentials =
                new AuthService.TokenRequest.PasswordCredentials();
        passwordCredentials.setUsername(tokenProperties.getUsername());
        passwordCredentials.setPassword(tokenProperties.getPassword());

        AuthService.TokenRequest.Auth auth = new AuthService.TokenRequest.Auth();
        auth.setTenantId(tokenProperties.getTenantId());
        auth.setPasswordCredentials(passwordCredentials);

        tokenRequest = new AuthService.TokenRequest();
        tokenRequest.setAuth(auth);

        restTemplate = new RestTemplate();

        String storageUrl = "https://kr1-api-object-storage.nhncloudservice.com/v1";
        String accountAuth = "/AUTH_fcb81f74e379456b8ca0e091d351a7af";
        String containerName = "/g2nee";
        storageProperties = new NhnCloudStorageProperties(storageUrl, accountAuth, containerName);

    }

    @Test
    @Order(1)
    @DisplayName("이미지 업로드 테스트")
    void uploadObject() throws FileNotFoundException {

        // given
        String objectPath = "/test";
        String objectName = "test.jpeg";
        String url =
                storageProperties.getStorageUrl() + storageProperties.getAuth() + storageProperties.getContainerName() +
                        objectPath + "/" + objectName;
        String tokenId = getTokenId();

//        File objFile = new File(objectPath + "/" + objectName);
//        InputStream inputStream = new FileInputStream(objFile);
        final RequestCallback requestCallback = request -> {
            request.getHeaders().add("Content-Type", "multipart/form-data");
            request.getHeaders().add("X-Auth-Token", tokenId);
//            IOUtils.copy(inputStream, request.getBody());
        };

        HttpMessageConverterExtractor<String> responseExtractor
                = new HttpMessageConverterExtractor<>(String.class, restTemplate.getMessageConverters());

        // when then
        assertDoesNotThrow(() -> {
            restTemplate.execute(url, HttpMethod.PUT, requestCallback, responseExtractor);
        });

    }

    @Test
    @Order(2)
    @DisplayName("이미지 삭제 테스트")
    void deleteObject() {

        // given
        String objectPath = "/test";
        String objectName = "test.jpeg";
        String url =
                storageProperties.getStorageUrl() + storageProperties.getAuth() + storageProperties.getContainerName() +
                        objectPath + "/" + objectName;
        String tokenId = getTokenId();

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Auth-Token", tokenId);
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        // when then
        assertDoesNotThrow(() -> {
            restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, String.class);
        });
    }

    private String getTokenId() {

        String url = tokenProperties.getUrl() + "tokens";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content_Type", "application/json");

        System.out.println(tokenRequest);
        HttpEntity<AuthService.TokenRequest> httpEntity = new HttpEntity<>(this.tokenRequest, headers);


        ResponseEntity<TokenResponseDto> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.POST,
                httpEntity,
                TokenResponseDto.class);

        return Objects.requireNonNull(responseEntity.getBody()).getAccess().getToken().getId();
    }
}
