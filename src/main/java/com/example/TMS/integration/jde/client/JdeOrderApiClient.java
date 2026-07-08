package com.example.TMS.integration.jde.client;

import com.example.TMS.integration.jde.dto.JdeOrderResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Component
@RequiredArgsConstructor
public class JdeOrderApiClient {

    @Qualifier("jdeWebClient")
    private final WebClient jdeWebClient;

    public JdeOrderResponse getOrders() {
        log.info("Calling JDE API to fetch orders");
        return jdeWebClient.get()
                .uri("/ORCHSales_Order")
                .retrieve()
                .bodyToMono(JdeOrderResponse.class)
                .block();
    }
}