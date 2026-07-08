package com.example.TMS.integration.jde.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class JdeTransportClientConfig {

    @Value("${jde.api.base-url}")
    private String baseUrl;

    @Value("${jde.api.username}")
    private String username;

    @Value("${jde.api.password}")
    private String password;

    @Bean(name = "jdeTransportWebClient")
    public WebClient jdeTransportWebClient(){
        return WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeaders(headers->headers.setBasicAuth(username, password))
                .build();

        }
    }

