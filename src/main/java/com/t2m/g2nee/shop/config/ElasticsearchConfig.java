package com.t2m.g2nee.shop.config;

import com.t2m.g2nee.shop.properties.NhnCloudKeyProperties;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;

@Configuration
public class ElasticsearchConfig extends AbstractElasticsearchConfiguration {

    private final NhnCloudKeyProperties nhnCloudKeyProperties;

    public ElasticsearchConfig(NhnCloudKeyProperties nhnCloudKeyProperties) {
        this.nhnCloudKeyProperties = nhnCloudKeyProperties;
    }

    @Override
    public RestHighLevelClient elasticsearchClient() {
        ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo(nhnCloudKeyProperties.getProperties(nhnCloudKeyProperties.getElasticsearchUrl()))
                .build();
        return RestClients.create(clientConfiguration).rest();
    }
}
