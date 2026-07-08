package com.example.TMS.integration.common;

import com.example.TMS.integration.common.domain.ErpOrder;
import java.util.List;

public interface ErpConnector {
    List<ErpOrder> fetchOrders();
    ErpOrder fetchOrder(String orderNumber);
}
