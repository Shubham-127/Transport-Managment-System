package com.example.TMS.integration.jde.mapper;

import com.example.TMS.integration.common.domain.ErpOrder;
import com.example.TMS.integration.common.domain.ErpOrderLine;
import com.example.TMS.integration.jde.dto.JdeOrder;
import com.example.TMS.integration.jde.dto.JdeOrderLines;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class JdeOrderMapper {
    public ErpOrder toErpOrder(JdeOrder jdeOrder){
        return ErpOrder.builder()
                .orderNumber(Long.parseLong(jdeOrder.getOrderNumber()))
                .orderType(jdeOrder.getOrderType())
                .company(jdeOrder.getCompany())
                .customerNumber(Long.parseLong(jdeOrder.getCustomerNumber()))
                .orderDate(jdeOrder.getOrderDate())
                .requestedDate(jdeOrder.getRequestedDate())
                .branchPlant(jdeOrder.getBranchPlant())
                .shipToCustomerNumber(jdeOrder.getShipToCustomerNumber())
                .shipToCustomerName(jdeOrder.getShipToCustomerName())
                .shipToAddress1(jdeOrder.getShipToAddress1())
                .shipToCity(jdeOrder.getShipToCity())
                .shipToState(jdeOrder.getShipToState())
                .shipToPinCode(jdeOrder.getShipToPinCode())
                .unitWeight(jdeOrder.getTotalWeight())
                .weightUnit(jdeOrder.getWeightUnit())
                .totalVolume(jdeOrder.getTotalVolume())
                .volumeUnit(jdeOrder.getVolumeUnit())
                .status(mapStatus(jdeOrder.getStatus()))
                .currencyCode(jdeOrder.getCurrencyCode())
                .lines(toErpLines(jdeOrder.getLines()))
                .build();
    }

    private List<ErpOrderLine> toErpLines(List<JdeOrderLines> jdeLines) {
        return jdeLines.stream().map(line -> ErpOrderLine.builder()
                .itemNumber(line.getItemNumber())
                .description(line.getDescription())
                .quantity(line.getQuantity())
                .uom(line.getUom())
                .unitPrice(line.getUnitPrice())
                .lineAmount(line.getLineAmount())
                .build()
        ).collect(Collectors.toList());
    }
    private String mapStatus(String jdeStatusCode){
        return switch (jdeStatusCode){
            case "540" -> "CONFIRMED";
            case "999" -> "CANCELLED";
            default -> "UNKNOWN";
        };
    }

}
