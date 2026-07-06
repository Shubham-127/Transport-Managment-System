package com.example.UserCRUD.integration.jde.PersistenceService;

import com.example.UserCRUD.integration.common.domain.ErpOrder;
import com.example.UserCRUD.integration.common.domain.ErpOrderLine;
import com.example.UserCRUD.model.CustomerMaster;
import com.example.UserCRUD.model.OrderLinesMaster;
import com.example.UserCRUD.model.OrderMaster;
import com.example.UserCRUD.repository.CustomerMasterRepository;
import com.example.UserCRUD.repository.OrderLinesMasterRepository;  // ← ADDED
import com.example.UserCRUD.repository.OrderMasterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class JdeOrderPersistenceService {

    private final OrderMasterRepository orderMasterRepository;
    private final CustomerMasterRepository customerMasterRepository;
    private final OrderLinesMasterRepository orderLinesMasterRepository; // ← ADDED

    @Transactional
    public void saveOrders(List<ErpOrder> erpOrders) {
        for (ErpOrder erpOrder : erpOrders) {
            try {
                saveOrUpdateOrder(erpOrder);
            } catch (Exception e) {
                log.error("Failed to save order {}: {}", erpOrder.getOrderNumber(), e.getMessage(), e);
            }
        }
    }

    private void saveOrUpdateOrder(ErpOrder erpOrder) {
        CustomerMaster customer = resolveCustomer(erpOrder);

        OrderMaster orderMaster = orderMasterRepository
                .findByOrderNumber(erpOrder.getOrderNumber())
                .orElseGet(OrderMaster::new);

        orderMaster.setOrderNumber(erpOrder.getOrderNumber());
        orderMaster.setOrderType(erpOrder.getOrderType());
        orderMaster.setCompany(erpOrder.getCompany());
        orderMaster.setCustomer(customer);
        orderMaster.setOrderDate(erpOrder.getOrderDate());
        orderMaster.setRequestedDate(erpOrder.getRequestedDate());
        orderMaster.setBranchPlant(erpOrder.getBranchPlant());
        orderMaster.setShipToCustomerNumber(erpOrder.getShipToCustomerNumber());
        orderMaster.setShipToCustomerName(erpOrder.getShipToCustomerName());
        orderMaster.setShipToAddress1(erpOrder.getShipToAddress1());
        orderMaster.setShipToCity(erpOrder.getShipToCity());
        orderMaster.setShipToState(erpOrder.getShipToState());
        orderMaster.setShipToPinCode(erpOrder.getShipToPinCode());
        orderMaster.setUnitWeight(erpOrder.getUnitWeight());
        orderMaster.setWeightUnit(erpOrder.getWeightUnit());
        orderMaster.setTotalVolume(erpOrder.getTotalVolume());         // unchanged — correct
        orderMaster.setVolumeUnit(erpOrder.getVolumeUnit());
        orderMaster.setStatus(erpOrder.getStatus());
        orderMaster.setCurrencyCode(erpOrder.getCurrencyCode());

        // ★ CHANGED: save parent first so it has a real DB id
        // before we delete/insert child lines against it
        OrderMaster savedOrder = orderMasterRepository.save(orderMaster);

        // ★ CHANGED: replaced getLines().clear() + getLines().addAll()
        // OLD: orderMaster.getLines().clear();
        // OLD: orderMaster.getLines().addAll(toOrderLines(...));
        //
        // WHY OLD CODE FAILED:
        // Lines collection is FetchType.LAZY — Hibernate never loads it
        // into memory unless something explicitly accesses it.
        // So .clear() was clearing an EMPTY in-memory list,
        // not actually deleting anything from the database.
        // Every sync run then .addAll() inserted 11 MORE rows
        // on top of the existing ones → 451 rows after 41 syncs.
        //
        // WHY NEW CODE WORKS:
        // deleteAllByOrderMaster() goes directly to the database:
        // DELETE FROM order_line_master WHERE order_master_id = ?
        // No lazy loading involved. Guaranteed to wipe existing rows.
        // Then saveAll() inserts exactly 11 fresh rows. Always 11, never growing.
        orderLinesMasterRepository.deleteAllByOrderMaster(savedOrder);

        List<OrderLinesMaster> freshLines = toOrderLines(erpOrder.getLines(), savedOrder);
        orderLinesMasterRepository.saveAll(freshLines);

        log.info("Saved order {} ({} lines)", savedOrder.getOrderNumber(), freshLines.size());
    }

    private CustomerMaster resolveCustomer(ErpOrder erpOrder) {
        return customerMasterRepository.findByCustomerId(erpOrder.getCustomerNumber())
                .orElseGet(() -> createMinimalCustomer(erpOrder));
    }

    private CustomerMaster createMinimalCustomer(ErpOrder erpOrder) {
        CustomerMaster customer = CustomerMaster.builder()
                .customerId(erpOrder.getCustomerNumber())
                .customerName(safe(erpOrder.getShipToCustomerName()))
                .gstNumber("N/A")
                .panNumber("N/A")
                .contactPerson("N/A")
                .mobile("N/A")
                .email("N/A")
                .address(safe(erpOrder.getShipToAddress1()))
                .city(safe(erpOrder.getShipToCity()))
                .state(safe(erpOrder.getShipToState()))
                .pincode(safe(erpOrder.getShipToPinCode()))
                .country("India")
                .active("true")
                .remarks("Auto-created from JDE order sync")
                .salesOrder("N/A")
                .build();

        log.warn("Auto-created CustomerMaster for customerId {} from JDE order {} — update with real customer details",
                customer.getCustomerId(), erpOrder.getOrderNumber());

        return customerMasterRepository.save(customer);
    }

    private String safe(String value) {
        return (value == null || value.isBlank()) ? "N/A" : value;
    }

    private List<OrderLinesMaster> toOrderLines(List<ErpOrderLine> erpLines, OrderMaster orderMaster) {
        return erpLines.stream().map(line -> OrderLinesMaster.builder()
                .lineNumber(line.getLineNumber())
                .itemNumber(line.getItemNumber())
                .description(line.getDescription())
                .quantity(line.getQuantity())
                .uom(line.getUom())
                .unitPrice(line.getUnitPrice())
                .lineAmount(line.getLineAmount())
                .unitWeight(line.getUnitWeight())
                .weightUnit(line.getWeightUnit())
                .unitVolume(line.getUnitVolume())
                .volumeUnit(line.getVolumeUnit())
                .orderMaster(orderMaster)
                .build()
        ).collect(Collectors.toList());
    }
}