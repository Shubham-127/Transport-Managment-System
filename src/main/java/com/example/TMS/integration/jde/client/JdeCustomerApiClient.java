package com.example.TMS.integration.jde.client;

import com.example.TMS.integration.jde.dto.JdeCustomerResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Component
public class JdeCustomerApiClient {
    private final WebClient jdeCustomerWebClient;

    public JdeCustomerApiClient(@Qualifier("jdeCustomerWebClient") WebClient jdeCustomerWebClient) {
        this.jdeCustomerWebClient = jdeCustomerWebClient;
    }
    public JdeCustomerResponse getCustomer(){
        log.info("Calling JDE order API...");
        return jdeCustomerWebClient.get()
                .uri("/ORCH_Customer_Data")
                .retrieve()
                .bodyToMono(JdeCustomerResponse.class)
                .block();
    }
}
