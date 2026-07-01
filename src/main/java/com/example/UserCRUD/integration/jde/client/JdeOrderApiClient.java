package com.example.UserCRUD.integration.jde.client;

import com.example.UserCRUD.integration.jde.dto.JdeOrderResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Component@RequiredArgsConstructor@Slf4j
public class JdeOrderApiClient {
    @Qualifier("JdeWebClient")
    private final WebClient jdeWebClient;

    public JdeOrderResponse getOrder(){
        log.info("Calling JDE order API");

        try{
            JdeOrderResponse response = jdeWebClient.get()
                    .uri("/ORCHSales_Order")
                    .retrieve()
                    .bodyToMono(JdeOrderResponse.class)
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
