package com.chlee021690.sampleproject.searchengine.config;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;

@Configuration
public class ElasticSearchConfig extends ElasticsearchConfiguration {

    @Value("${spring.elasticsearch.uris}")
    private String esHost;

    @Override
    public ClientConfiguration clientConfiguration() {
        URI uri = URI.create(esHost);

        String[] userInfo = uri.getUserInfo().split(":");

        return ClientConfiguration.builder()
                .connectedTo(uri.getHost() + ":" + uri.getPort())
                .withBasicAuth(userInfo[0], userInfo[1])
                .build();
    }

}
