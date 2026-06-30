package com.example.UserCRUD.integration.jde.client;

import com.example.UserCRUD.integration.jde.dto.JdeOrderResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URI;

@Component
public class JdeApiClient {
    private final WebClient jdeWebCLient;

    public JdeApiClient(@Qualifier("jdeWebClient") WebClient jdeWebCLient) {
        this.jdeWebCLient = jdeWebCLient;
    }
    public JdeOrderResponse getOrders(){
        return jdeWebCLient.get()

                .retrieve()
                .bodyToMono(JdeOrderResponse.class)
                .block();
    }
}
