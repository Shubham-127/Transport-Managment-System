package com.example.UserCRUD.integration.jde.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Configuration
public class JdeClientConfig {
    @Value("${jde.api.base-url}")
    private String baseUrl;

    @Value("${jde.api.username}")
    private String username;

    @Value("${jde.api.password}")
    private String password;

    @Bean(name = "jdeWebClient")
    public WebClient jdeWebClient() {
        String credentials = username + ":" + password;
        String encodedCredentials = Base64.getEncoder()
                .encodeToString(credentials.getBytes(StandardCharsets.UTF_8));

        return WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader("Authorization", "Basic " + encodedCredentials)
                .build();
    }
}
