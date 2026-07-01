package com.example.UserCRUD.integration.jde.client;
import com.example.UserCRUD.integration.jde.dto.JdeTransportResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JdeTransportApiClient {

    @Qualifier("JdeTransportWebClient")
    private final WebClient jdeTransportWebClient;

    public JdeTransportApiClient getTransportMaster(){
        log.info("Calling JDE Api to fetch Transport Master");

        try{
            JdeTransportResponse response = jdeTransportWebClient.get()
                    .uri("/ORCH_GetTransportWithVehicles")
                    .retrieve()
                    .bodyToMono(JdeTransportResponse.class)
                    .block();
            log.info("Successfully received response from JDE");
            return response;
        }catch (WebClientResponseException e){
            log.error("JDE returned HTTP{}:{}", e.getStatusCode(), e.getResponseBodyAsString());

            throw e;
        }catch (Exception e){
            log.error("Failed to call JDE order API", e);
            throw e;
        }
    }
}
