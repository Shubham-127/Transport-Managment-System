package com.example.TMS.service;

import com.example.TMS.dto.request.Create.CreateOrderMasterRequest;
import com.example.TMS.dto.request.Update.UpdateOrderMasterRequest;
import com.example.TMS.dto.response.OrderMasterResponse;
import java.util.List;

public interface OrderMasterService {

    OrderMasterResponse createOrder(CreateOrderMasterRequest request);

    List<OrderMasterResponse> getAllOrders();

    OrderMasterResponse getOrderById(Long id);

    OrderMasterResponse updateOrder(Long id, UpdateOrderMasterRequest request);

    void deleteOrder(Long id);
}