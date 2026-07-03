package com.example.UserCRUD.integration.jde.scheduler;

import com.example.UserCRUD.integration.jde.PersistenceService.JdeTransportPersistenceService;
import com.example.UserCRUD.integration.jde.client.JdeTransportApiClient;
import com.example.UserCRUD.integration.jde.dto.JdeTransportResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class JdeTransportSyncScheduler {

    private final JdeTransportApiClient jdeTransportApiClient;
    private final JdeTransportPersistenceService jdeTransportPersistenceService;

    @Scheduled(fixedRateString = "${jde.transport.sync.fixed-rate-ms}")
    public void syncTransportMastersFromJde() {
        log.info("Starting scheduled JDE transport sync...");

        try {
            JdeTransportResponse response = jdeTransportApiClient.getTransportMasters();

            log.info("Fetched {} transport masters from JDE", response.getCount());

            jdeTransportPersistenceService.saveTransportMasters(response.getTransportMasters());

            log.info("JDE transport sync completed successfully");

        } catch (Exception e) {
            log.error("JDE transport sync failed: {}", e.getMessage(), e);
        }
    }
}