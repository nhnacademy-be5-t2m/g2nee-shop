package com.t2m.g2nee.shop.config;

import com.t2m.g2nee.shop.properties.DataSourceProperties;
import com.t2m.g2nee.shop.properties.NhnCloudKeyProperties;
import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatasourceConfig {

    private final NhnCloudKeyProperties nhnCloudKeyProperties;

    public DatasourceConfig(NhnCloudKeyProperties nhnCloudKeyProperties) {
        this.nhnCloudKeyProperties = nhnCloudKeyProperties;
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

        String url = nhnCloudKeyProperties.getProperties(nhnCloudKeyProperties.getDbUrlKeyId());
        String username = nhnCloudKeyProperties.getProperties(nhnCloudKeyProperties.getDbUsernameKeyId());
        String password = nhnCloudKeyProperties.getProperties(nhnCloudKeyProperties.getDbPasswordKeyId());

        return DataSourceProperties.builder()
                .url(url)
                .username(username)
                .password(password)
                .build();
    }
}
