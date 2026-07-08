package com.example.TMS.service;


import com.example.TMS.dto.request.Create.CreateItemMasterRequest;
import com.example.TMS.dto.request.Update.UpdateItemMasterRequest;
import com.example.TMS.dto.response.ItemMasterResponse;

import java.util.List;

public interface ItemMasterService {

    ItemMasterResponse createItem(CreateItemMasterRequest request);
    List<ItemMasterResponse> getAllItems();
    ItemMasterResponse getItemById(Long id);
    ItemMasterResponse updateItem(Long id, UpdateItemMasterRequest request);
    void deleteItem(Long id);
}
