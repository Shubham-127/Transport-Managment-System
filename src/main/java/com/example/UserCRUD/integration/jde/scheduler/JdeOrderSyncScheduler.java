package com.example.UserCRUD.integration.jde.scheduler;

import com.example.UserCRUD.integration.common.ErpConnectorResolver;
import com.example.UserCRUD.integration.common.ErpType;
import com.example.UserCRUD.integration.common.domain.ErpOrder;
import com.example.UserCRUD.integration.jde.PersistenceService.JdeOrderPersistenceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class JdeOrderSyncScheduler {

    private final ErpConnectorResolver erpConnectorResolver;
    private final JdeOrderPersistenceService jdeOrderPersistenceService;   // ← add this field

    @Scheduled(fixedRateString = "${jde.sync.fixed-rate-ms}")
    public void syncOrdersFromJde() {
        log.info("Starting scheduled JDE order sync...");

        try {
            List<ErpOrder> orders = erpConnectorResolver
                    .resolve(ErpType.JDE)
                    .fetchOrders();

            log.info("Fetched {} orders from JDE", orders.size());

            jdeOrderPersistenceService.saveOrders(orders);   // ← call it on the instance, not the class

            log.info("JDE order sync completed successfully");

        } catch (Exception e) {
            log.error("JDE order sync failed: {}", e.getMessage(), e);
        }
    }
}