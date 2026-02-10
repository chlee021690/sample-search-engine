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

    @Value("${spring.elasticsearch.username}")
    private String esUsername;

    @Value("${spring.elasticsearch.password}")
    private String esPassword;

    @Override
    public ClientConfiguration clientConfiguration() {
        URI uri = URI.create(esHost);

        String[] userInfo = uri.getUserInfo() != null ? uri.getUserInfo().split(":", 2) : new String[0];
        String username = (esUsername != null && !esUsername.isBlank())
            ? esUsername
            : (userInfo.length > 0 ? userInfo[0] : null);
        String password = (esPassword != null && !esPassword.isBlank())
            ? esPassword
            : (userInfo.length > 1 ? userInfo[1] : null);

        int port = uri.getPort();
        if (port == -1) {
            port = "https".equalsIgnoreCase(uri.getScheme()) ? 443 : 9200;
        }

        var baseBuilder = ClientConfiguration.builder()
            .connectedTo(uri.getHost() + ":" + port);

        if ("https".equalsIgnoreCase(uri.getScheme())) {
            var secureBuilder = baseBuilder.usingSsl();
            return (username != null && password != null)
                ? secureBuilder.withBasicAuth(username, password).build()
                : secureBuilder.build();
        }

        return (username != null && password != null)
            ? baseBuilder.withBasicAuth(username, password).build()
            : baseBuilder.build();
    }

}
