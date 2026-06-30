package com.example.UserCRUD.integration.jde.PersistenceService;

import com.example.UserCRUD.integration.common.domain.ErpOrder;
import com.example.UserCRUD.integration.common.domain.ErpOrderLine;
import com.example.UserCRUD.model.CustomerMaster;
import com.example.UserCRUD.model.OrderLinesMaster;
import com.example.UserCRUD.model.OrderMaster;
import com.example.UserCRUD.repository.CustomerMasterRepository;
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
        orderMaster.setTotalVolume(erpOrder.getTotalVolume());
        orderMaster.setVolumeUnit(erpOrder.getVolumeUnit());
        orderMaster.setStatus(erpOrder.getStatus());
        orderMaster.setCurrencyCode(erpOrder.getCurrencyCode());

        orderMaster.getLines().clear();
        orderMaster.getLines().addAll(toOrderLines(erpOrder.getLines(), orderMaster));

        orderMasterRepository.save(orderMaster);
        log.info("Saved order {} ({} lines)", orderMaster.getOrderNumber(), orderMaster.getLines().size());
    }

    private CustomerMaster resolveCustomer(ErpOrder erpOrder) {
        return customerMasterRepository.findByCustomerId(erpOrder.getCustomerNumber())
                .orElseGet(() -> createMinimalCustomer(erpOrder));
    }

    private CustomerMaster createMinimalCustomer(ErpOrder erpOrder) {
        CustomerMaster customer = CustomerMaster.builder()
                .customerId(erpOrder.getCustomerNumber())
                .customerName(safe(erpOrder.getShipToCustomerName()))
                .getNumber("N/A")
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