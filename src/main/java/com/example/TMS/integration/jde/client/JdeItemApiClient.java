package com.example.TMS.integration.jde.client;

import com.example.TMS.integration.jde.dto.JdeItemResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Component
public class JdeItemApiClient {

    private final WebClient jdeItemWebClient;

    public JdeItemApiClient(@Qualifier("jdeItemWebClient") WebClient jdeItemWebClient) {
        this.jdeItemWebClient = jdeItemWebClient;
    }
    public JdeItemResponse getItem(){
        log.info("Calling JDE API to fetch transport master");
        return jdeItemWebClient.get()
                .uri("/ORCH_Item_Master")
                .retrieve()
                .bodyToMono(JdeItemResponse.class)
                .block();
    }
}
