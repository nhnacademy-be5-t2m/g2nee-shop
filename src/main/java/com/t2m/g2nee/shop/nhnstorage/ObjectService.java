package com.t2m.g2nee.shop.nhnstorage;

import com.t2m.g2nee.shop.properties.NhnCloudStorageProperties;
import java.io.InputStream;
import java.util.List;
import lombok.Data;
import lombok.NonNull;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
@Transactional
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

    /**
     * nhncloud api를 호출하여 storage에 이미지를 저장하는 메서드
     *
     * @param tokenId     인증 토큰 String 값
     * @param objectPath  폴더이름, 없으면 생성됨
     * @param objectName  파일 이름
     * @param inputStream
     */
    public String uploadObject(String tokenId, String objectPath, String objectName, final InputStream inputStream) {
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

        return url;
    }

    /**
     * nhncloud api를 호출하여 storage에 이미지를 저장하는 메서드
     *
     * @param tokenId 인증토큰 Id값
     * @param urls     삭제할 파일 url들
     */
    public void deleteObject(List<String> urls, String tokenId) {

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Auth-Token", tokenId);

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        for (String url : urls) {
            this.restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, String.class);
        }
    }
}

