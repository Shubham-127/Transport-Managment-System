package com.example.UserCRUD.integration.jde.connector;

import com.example.UserCRUD.integration.common.ErpConnector;
import com.example.UserCRUD.integration.common.domain.ErpOrder;
import com.example.UserCRUD.integration.jde.client.JdeApiClient;
import com.example.UserCRUD.integration.jde.dto.JdeOrderResponse;
import com.example.UserCRUD.integration.jde.mapper.JdeOrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component("jdeConnector")
@RequiredArgsConstructor
public class JdeConnector implements ErpConnector {

    private final JdeApiClient jdeApiClient;
    private final JdeOrderMapper jdeOrderMapper;

    @Override
    public List<ErpOrder> fetchOrders() {
        JdeOrderResponse rawResponse = jdeApiClient.getOrders();
        return rawResponse.getOrders().stream()
                .map(jdeOrderMapper::toErpOrder)
                .collect(Collectors.toList());
    }

    @Override
    public ErpOrder fetchOrder(String orderNumber) {
        return fetchOrders().stream()
                .filter(order -> order.getOrderNumber().equals(orderNumber))
                .findFirst()
                .orElseThrow(() -> new RuntimeException(" order not found: " + orderNumber));
    }
}
