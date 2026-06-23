package com.example.UserCRUD.service;

import com.example.UserCRUD.dto.request.Create.CreateOrderMasterRequest;
import com.example.UserCRUD.dto.request.Update.UpdateOrderMasterRequest;
import com.example.UserCRUD.dto.response.OrderMasterResponse;
import java.util.List;

public interface OrderMasterService {

    OrderMasterResponse createOrder(CreateOrderMasterRequest request);

    List<OrderMasterResponse> getAllOrders();

    OrderMasterResponse getOrderById(Long id);

    OrderMasterResponse updateOrder(Long id, UpdateOrderMasterRequest request);

    void deleteOrder(Long id);
}