package com.t2m.g2nee.shop.config;

import com.t2m.g2nee.shop.properties.DataSourceProperties;
import com.t2m.g2nee.shop.properties.NhnProperties;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;
import java.time.Duration;
import java.util.List;

@Configuration
public class JavaConfig {

    private NhnCloudKey nhnCloudKey;

    private final String URL;

    public JavaConfig(NhnCloudKey nhnCloudKey) {
        this.nhnCloudKey = nhnCloudKey;
        this.URL = nhnCloudKey.getEndpoint() + nhnCloudKey.getPath() + nhnCloudKey.getAppKey();

    }

    @Bean
    public DataSource dataSource() {

        DataSourceProperties dataSourceProperties = getDataSourceProperties();

        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setDriverClassName(dataSourceProperties.getDriverClassName());
        basicDataSource.setUrl(dataSourceProperties.getUrl());
        basicDataSource.setUsername(dataSourceProperties.getUsername());
        basicDataSource.setPassword(dataSourceProperties.getPassword());
        basicDataSource.setValidationQuery("SELECT 1");
        basicDataSource.setMinIdle(10);
        basicDataSource.setMaxIdle(20);
        return basicDataSource;

    }

    @Bean
    RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
                .setConnectTimeout(Duration.ofSeconds(5L))
                .setReadTimeout(Duration.ofSeconds(5L))
                .build();
    }

    /**
     * nhncloud Api를 이용해서 DataSource properties를 가져오는 메서드
     *
     * @return Datasource의 설정 값들이 담긴 DataSourceProperties 객체
     **/
    public DataSourceProperties getDataSourceProperties() {

        String driverClassName = getProperties(nhnCloudKey.getDriverClassNameKeyId());
        String url = getProperties(nhnCloudKey.getUrlKeyId());
        String username = getProperties(nhnCloudKey.getUsernameKeyId());
        String password = getProperties(nhnCloudKey.getPasswordKeyId());

        return DataSourceProperties.builder()
                .driverClassName(driverClassName)
                .url(url)
                .username(username)
                .password(password)
                .build();
    }
    /**
     * nhncloud Api를 이용해서 properties를 가져오는 메서드
     * @param keyId nhncloud 기밀데이터를 불러오기 위한 keyId 값
     * @return 기밀데이터의 string 값
     **/
    public String getProperties(String keyId){

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<String> requestEntity = new HttpEntity<>(httpHeaders);
        ResponseEntity<NhnProperties> exchange = restTemplate.exchange(URL + keyId,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<>() {
                });

        return exchange.getBody().getBody().getSecret().getValue();

    }
}
