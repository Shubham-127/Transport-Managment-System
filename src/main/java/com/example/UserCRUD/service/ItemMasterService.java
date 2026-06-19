package com.example.UserCRUD.service;


import com.example.UserCRUD.dto.request.CreateItemMasterRequest;
import com.example.UserCRUD.dto.request.UpdateItemMasterRequest;
import com.example.UserCRUD.dto.response.ItemMasterResponse;

import java.util.List;

public interface ItemMasterService {

    ItemMasterResponse createItem(CreateItemMasterRequest request);
    List<ItemMasterResponse> getAllItems();
    ItemMasterResponse getItemById(Long id);
    ItemMasterResponse updateItem(Long id, UpdateItemMasterRequest request);
    void deleteItem(Long id);
}
