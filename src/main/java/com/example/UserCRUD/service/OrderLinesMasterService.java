package com.example.UserCRUD.service;

import com.example.UserCRUD.dto.request.Create.CreateOrderLinesMasterRequest;
import com.example.UserCRUD.dto.request.Update.UpdateOrderLinesMasterRequest;
import com.example.UserCRUD.dto.response.OrderLinesMasterResponse;
import java.util.List;

public interface OrderLinesMasterService {

    // orderId comes from the URL — this line gets attached to that order
    OrderLinesMasterResponse createLine(Long orderId, CreateOrderLinesMasterRequest request);

    // returns ALL lines belonging to one specific order
    List<OrderLinesMasterResponse> getLinesByOrderId(Long orderId);

    OrderLinesMasterResponse getLineById(Long id);

    OrderLinesMasterResponse updateLine(Long id, UpdateOrderLinesMasterRequest request);

    void deleteLine(Long id);
}