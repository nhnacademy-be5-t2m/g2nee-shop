package com.t2m.g2nee.shop.config;

import com.t2m.g2nee.shop.properties.DataSourceProperties;
import com.t2m.g2nee.shop.dto.KeyResponseDto;
import com.t2m.g2nee.shop.properties.NhnCloudProperties;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;
import java.util.List;
import java.util.Objects;

@Configuration
public class DatasourceConfig {

    private final NhnCloudProperties nhnCloudProperties;
    private final String URL;

    public DatasourceConfig(NhnCloudProperties nhnCloudProperties) {
        this.nhnCloudProperties = nhnCloudProperties;
        this.URL = nhnCloudProperties.getUrl() + nhnCloudProperties.getPath() + nhnCloudProperties.getAppKey();

    }

    @Bean
    public DataSource dataSource() {


        DataSourceProperties dataSourceProperties = getDataSourceProperties();

        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        basicDataSource.setUrl(dataSourceProperties.getUrl());
        basicDataSource.setUsername(dataSourceProperties.getUsername());
        basicDataSource.setPassword(dataSourceProperties.getPassword());
        basicDataSource.setValidationQuery("SELECT 1");
        basicDataSource.setMinIdle(10);
        basicDataSource.setMaxIdle(20);
        return basicDataSource;

    }

    /**
     * nhncloud Api를 이용해서 DataSource properties를 가져오는 메서드
     * @return Datasource의 설정 값들이 담긴 DataSourceProperties 객체
     **/
    public DataSourceProperties getDataSourceProperties() {

        String url = getProperties(nhnCloudProperties.getUrlKeyId());
        String username = getProperties(nhnCloudProperties.getUsernameKeyId());
        String password = getProperties(nhnCloudProperties.getPasswordKeyId());

        return DataSourceProperties.builder()
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
        ResponseEntity<KeyResponseDto> exchange = restTemplate.exchange(URL + keyId,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<>() {
                });

        return Objects.requireNonNull(exchange.getBody()).getBody().getSecret().getValue();

    }
}
