package com.example.TMS.controller;

import com.example.TMS.dto.request.Create.CreateItemMasterRequest;
import com.example.TMS.dto.request.Update.UpdateItemMasterRequest;
import com.example.TMS.dto.response.ItemMasterResponse;
import com.example.TMS.service.ItemMasterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/items")
@RequiredArgsConstructor
public class ItemMasterController {

private final ItemMasterService itemService;

@PostMapping
@PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public ResponseEntity<ItemMasterResponse> createItem(@RequestBody CreateItemMasterRequest request){
    return ResponseEntity.status(HttpStatus.CREATED).body(itemService.createItem(request));
}

@GetMapping
@PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<ItemMasterResponse>> getAllItems(){
    return ResponseEntity.ok(itemService.getAllItems());

}

@GetMapping("/{id}")
@PreAuthorize("isAuthenticated()")
    public ResponseEntity<ItemMasterResponse> getItemById(@PathVariable Long id){
    return ResponseEntity.ok(itemService.getItemById(id));
}

@PutMapping("/{id}")
@PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public ResponseEntity<ItemMasterResponse> updateItem(@PathVariable Long id,
                                                         @RequestBody UpdateItemMasterRequest request ){
    return ResponseEntity.ok(itemService.updateItem(id, request));

}

@DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
    itemService.deleteItem(id);
    return ResponseEntity.noContent().build();
}
}
