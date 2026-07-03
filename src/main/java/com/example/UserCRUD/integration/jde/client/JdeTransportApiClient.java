package com.example.UserCRUD.integration.jde.client;

import com.example.UserCRUD.integration.jde.dto.JdeTransportResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Component
@RequiredArgsConstructor
public class JdeTransportApiClient {

    @Qualifier("jdeTransportWebClient")
    private final WebClient jdeTransportWebClient;

    public JdeTransportResponse getTransportMasters() {
        log.info("Calling JDE API to fetch transport masters");
        return jdeTransportWebClient.get()
                .uri("/ORCH_GetTransportWithVehicles")
                .retrieve()
                .bodyToMono(JdeTransportResponse.class)
                .block();
    }
}