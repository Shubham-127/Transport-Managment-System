package com.example.TMS.integration.jde.scheduler;

import com.example.TMS.integration.jde.PersistenceService.JdeItemPersistenceService;
import com.example.TMS.integration.jde.client.JdeItemApiClient;
import com.example.TMS.integration.jde.dto.JdeItemResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor@Component
public class JdeItemSyncScheduler {
    private final JdeItemApiClient jdeItemApiClient;
    private final JdeItemPersistenceService jdeItemPersistenceService;

    @Scheduled(fixedRateString = "${jde.transport.sync.fixed-rate-ms}")

    public void syncOrderFromJde(){
        log.info("Starting the scheduled JDE order Sync...");

        try {
            JdeItemResponse response = jdeItemApiClient.getItem();
            log.info("fetch {} item from JDE", response.getCount());

            jdeItemPersistenceService.saveItemMaster(
                    response.getItemMaster());

            log.info("JDE transport sync completed successfully");

        }catch (Exception e){
            log.error("JDE item Sync faild:{}", e.getMessage(), e);
        }
    }
}
