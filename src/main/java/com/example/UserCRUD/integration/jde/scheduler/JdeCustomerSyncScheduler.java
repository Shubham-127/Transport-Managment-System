package com.example.UserCRUD.integration.jde.scheduler;

import com.example.UserCRUD.integration.jde.PersistenceService.JdeCustomerPersistenceService;
import com.example.UserCRUD.integration.jde.client.JdeCustomerApiClient;
import com.example.UserCRUD.integration.jde.dto.JdeCustomerResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class JdeCustomerSyncScheduler {

    private final JdeCustomerApiClient jdeCustomerApiClient;
    private final JdeCustomerPersistenceService jdeCustomerPersistenceService;

    @Scheduled(fixedRateString = "${jde.customer.sync.fixed-rate-ms}")
    public void syncCustomerFromJde() {

        log.info("Starting the scheduled JDE Customer Sync...");

        try {

            JdeCustomerResponse response = jdeCustomerApiClient.getCustomer();

            log.info("Fetched {} customers from JDE", response.getCount());

            jdeCustomerPersistenceService.saveCustomerMaster(
                    response.getCustomers()
            );

            log.info("JDE Customer Sync completed successfully.");

        } catch (Exception e) {

            log.error("JDE Customer Sync failed: {}", e.getMessage(), e);

        }
    }
}
