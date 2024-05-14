package com.t2m.g2nee.shop.config;

import com.t2m.g2nee.shop.dto.KeyResponseDto;
import com.t2m.g2nee.shop.properties.DataSourceProperties;
import com.t2m.g2nee.shop.properties.NhnCloudKeyProperties;
import java.util.List;
import java.util.Objects;
import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;
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
public class DatasourceConfig {

    private final NhnCloudKeyProperties nhnCloudKeyProperties;
    private final String nhnCloudUrl;

    public DatasourceConfig(NhnCloudKeyProperties nhnCloudKeyProperties) {
        this.nhnCloudKeyProperties = nhnCloudKeyProperties;
        this.nhnCloudUrl =
                nhnCloudKeyProperties.getUrl() + nhnCloudKeyProperties.getPath() + nhnCloudKeyProperties.getAppKey();

    }

    @Bean
    public DataSource dataSource() {


        DataSourceProperties dataSourceProperties = getDataSourceProperties();

        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        basicDataSource.setUrl(dataSourceProperties.getUrl() + "?rewriteBatchedStatements=true");
        basicDataSource.setUsername(dataSourceProperties.getUsername());
        basicDataSource.setPassword(dataSourceProperties.getPassword());
        basicDataSource.setValidationQuery("SELECT 1");
        basicDataSource.setMinIdle(20);
        basicDataSource.setMaxIdle(20);
        basicDataSource.setMaxTotal(20);
        basicDataSource.setInitialSize(20);
        return basicDataSource;

    }

    /**
     * nhncloud Api를 이용해서 DataSource properties를 가져오는 메서드
     *
     * @return Datasource의 설정 값들이 담긴 DataSourceProperties 객체
     **/
    public DataSourceProperties getDataSourceProperties() {

        String url = getProperties(nhnCloudKeyProperties.getUrlKeyId());
        String username = getProperties(nhnCloudKeyProperties.getUsernameKeyId());
        String password = getProperties(nhnCloudKeyProperties.getPasswordKeyId());

        return DataSourceProperties.builder()
                .url(url)
                .username(username)
                .password(password)
                .build();
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
        ResponseEntity<KeyResponseDto> exchange = restTemplate.exchange(nhnCloudUrl + keyId,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<>() {
                });

        return Objects.requireNonNull(exchange.getBody()).getBody().getSecret().getValue();

    }
}
