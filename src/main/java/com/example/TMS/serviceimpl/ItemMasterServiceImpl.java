package com.example.TMS.serviceimpl;

import com.example.TMS.dto.request.Create.CreateItemMasterRequest;
import com.example.TMS.dto.request.Update.UpdateItemMasterRequest;
import com.example.TMS.dto.response.ItemMasterResponse;
import com.example.TMS.exception.ResourceNotFoundException;
import com.example.TMS.model.ItemMaster;
import com.example.TMS.repository.ItemMasterRepository;
import com.example.TMS.service.ItemMasterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemMasterServiceImpl implements ItemMasterService {

    private final ItemMasterRepository itemMasterRepository;

    @Override
    public ItemMasterResponse createItem(CreateItemMasterRequest request) {
        if(itemMasterRepository.existsByItemNumber(request.getItemNumber())){
            throw new RuntimeException("Item already exists with itemNumber;" + request.getItemNumber());
        }
        ItemMaster item = ItemMaster.builder()
                .itemNumber(request.getItemNumber())
                .shortItemNo(request.getShortItemNo())
                .description(request.getDescription())
                .description2(request.getDescription2())
                .thirdItemNumber(request.getThirdItemNumber())
                .lineType(request.getLineType())
                .stockingType(request.getStockingType())
                .build();

        ItemMaster saved = itemMasterRepository.save(item);
        return mapToResponseDTO(saved);
    }

    @Override
    public List<ItemMasterResponse> getAllItems() {
        return itemMasterRepository.findAll()
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ItemMasterResponse getItemById(Long id) {
        ItemMaster item = itemMasterRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found with id:" +id));
        return mapToResponseDTO(item);
    }

    @Override
    public ItemMasterResponse updateItem(Long id, UpdateItemMasterRequest request) {
        ItemMaster existing = itemMasterRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Item not found with id:" + id));
        ItemMaster updatedItem = ItemMaster.builder()
                .id(existing.getId())
                .itemNumber(request.getItemNumber())
                .shortItemNo(request.getShortItemNo())
                .description(request.getDescription())
                .description2(request.getDescription2())
                .thirdItemNumber(request.getThirdItemNumber())
                .lineType(request.getLineType())
                .stockingType(request.getStockingType())
                .build();

        // save() on an entity WITH an existing id → UPDATE SQL
        ItemMaster saved = itemMasterRepository.save(updatedItem);

        return mapToResponseDTO(saved);
    }

    @Override
    public void deleteItem(Long id) {
        ItemMaster item = itemMasterRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found with id: " + id));

        itemMasterRepository.delete(item);
    }
    private ItemMasterResponse mapToResponseDTO(ItemMaster item) {
        return ItemMasterResponse.builder()
                .id(item.getId())
                .itemNumber(item.getItemNumber())
                .shortItemNo(item.getShortItemNo())
                .description(item.getDescription())
                .description2(item.getDescription2())
                .thirdItemNumber(item.getThirdItemNumber())
                .lineType(item.getLineType())
                .stockingType(item.getStockingType())
                .build();
    }

}
