package com.t2m.g2nee.shop.nhnstorage;

import com.sun.istack.NotNull;
import com.t2m.g2nee.shop.properties.NhnCloudStorageProperties;
import com.t2m.g2nee.shop.properties.NhnCloudTokenProperties;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import lombok.Data;
import lombok.NonNull;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpMessageConverterExtractor;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.RestTemplate;

/**
 * objectStorage 로직이 있는 Service 입니다.
 *
 * @author : 신동민
 * @since : 1.0
 */
@Data
@Service
public class ObjectService {

    private RestTemplate restTemplate;
    private AuthService authService;
    private NhnCloudStorageProperties nhnCloudStorageProperties;

    public ObjectService(NhnCloudStorageProperties nhnCloudStorageProperties, AuthService authService) {
        // 오버라이드한 RequestCallback을 사용할 수 있도록 설정
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setBufferRequestBody(false);
        this.restTemplate = new RestTemplate(requestFactory);
        this.nhnCloudStorageProperties = nhnCloudStorageProperties;
        this.authService = authService;
    }

    public static void main(String[] args) {
        final String storageUrl = "https://kr1-api-object-storage.nhncloudservice.com/v1";
        final String auth = "/AUTH_fcb81f74e379456b8ca0e091d351a7af";
        final String containerName = "/g2nee";
        final String objectPath = "/thumbnail";
        final String objectName = "TheMartian.jpeg";

        final String authUrl = "https://api-identity-infrastructure.nhncloudservice.com/v2.0";
        final String tenantId = "fcb81f74e379456b8ca0e091d351a7af";
        final String username = "aokagami03@gmail.com";
        final String password = "g2nee";

        NhnCloudTokenProperties nhnCloudTokenProperties =
                new NhnCloudTokenProperties(authUrl, tenantId, username, password);

        AuthService authService = new AuthService(nhnCloudTokenProperties);
        String tokenId = authService.requestToken();

        NhnCloudStorageProperties properties = new NhnCloudStorageProperties(storageUrl, auth, containerName);
        ObjectService objectService = new ObjectService(properties, authService);

        try {
            // 파일로 부터 InputStream 생성
            File objFile = new File(objectPath + "/" + objectName);
            InputStream inputStream = new FileInputStream(objFile);

            // 업로드
            objectService.uploadObject(tokenId, objectPath, objectName, inputStream);
            System.out.println("\nUpload OK");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 오브젝트 업로드 api 접근을 위한 url을 만드는 메서드
     *
     * @param properties storage properties가 담긴 객체
     * @param objectPath 이미지 경로
     * @param objectName 저장할 이미지 이름
     * @return String 형식의 url
     */
    private String getUrl(NhnCloudStorageProperties properties, @NonNull String objectPath,
                          @NonNull String objectName) {
        return properties.getStorageUrl() + properties.getAuth() + properties.getContainerName() + "/" + objectPath +
                "/" + objectName;
    }

    private String getUrl(NhnCloudStorageProperties properties, @NotNull String objectName) {
        return properties.getStorageUrl() + properties.getAuth() + properties.getContainerName() + "/" + objectName;
    }

    /**
     * nhncloud api를 호출하여 storage에 이미지를 저장하는 메서드
     *
     * @param tokenId     인증 토큰 String 값
     * @param objectPath  폴더이름, 없으면 생성됨
     * @param objectName  파일 이름
     * @param inputStream
     */
    public void uploadObject(String tokenId, String objectPath, String objectName, final InputStream inputStream) {
        String url = this.getUrl(nhnCloudStorageProperties, objectPath, objectName);

        // InputStream을 요청 본문에 추가할 수 있도록 RequestCallback 오버라이드
        final RequestCallback requestCallback = request -> {
            request.getHeaders().add("Content-Type", "multipart/form-data");
            request.getHeaders().add("X-Auth-Token", tokenId);
            IOUtils.copy(inputStream, request.getBody());
        };

        HttpMessageConverterExtractor<String> responseExtractor
                = new HttpMessageConverterExtractor<>(String.class, restTemplate.getMessageConverters());

        // API 호출
        restTemplate.execute(url, HttpMethod.PUT, requestCallback, responseExtractor);
    }

    /**
     * nhncloud api를 호출하여 storage에 이미지를 저장하는 메서드
     *
     * @param tokenId    인증토큰 Id값
     * @param objectName 삭제할 파일 이름
     */
    public void deleteObject(String objectName, String tokenId) {
        String url = this.getUrl(nhnCloudStorageProperties, objectName);

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Auth-Token", tokenId);

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        this.restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, String.class);
    }
}

