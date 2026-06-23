package com.example.UserCRUD.serviceimpl;

import com.example.UserCRUD.dto.request.Create.CreateOrderLinesMasterRequest;
import com.example.UserCRUD.dto.request.Update.UpdateOrderLinesMasterRequest;
import com.example.UserCRUD.dto.response.OrderLinesMasterResponse;
import com.example.UserCRUD.exception.ResourceNotFoundException;
import com.example.UserCRUD.model.OrderLinesMaster;
import com.example.UserCRUD.model.OrderMaster;
import com.example.UserCRUD.repository.OrderLinesMasterRepository;
import com.example.UserCRUD.repository.OrderMasterRepository;
import com.example.UserCRUD.service.OrderLinesMasterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

// @Service registers this as a Spring-managed bean
// @RequiredArgsConstructor generates a constructor for every
// "private final" field below, which is how Spring injects
// both repositories automatically
@Service
@RequiredArgsConstructor
public class OrderLinesMasterServiceImpl implements OrderLinesMasterService {

    private final OrderLinesMasterRepository orderLinesMasterRepository;

    // Needed to confirm the parent order actually exists
    // before attaching a line to it
    private final OrderMasterRepository orderMasterRepository;

    @Override
    public OrderLinesMasterResponse createLine(Long orderId, CreateOrderLinesMasterRequest request) {

        // Step 1 — confirm the parent order actually exists
        // This prevents creating "orphan" lines pointing to a
        // non-existent order
        OrderMaster order = orderMasterRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + orderId));

        // Step 2 — build the line using the builder pattern,
        // explicitly setting the foreign key to the parent order's id
        OrderLinesMaster line = OrderLinesMaster.builder()
                .orderMaster(order)
                .lineNumber(request.getLineNumber())
                .itemNumber(request.getItemNumber())
                .description(request.getDescription())
                .quantity(request.getQuantity())
                .uom(request.getUom())
                .unitPrice(request.getUnitPrice())
                .lineAmount(request.getLineAmount())
                .unitWeight(request.getUnitWeight())
                .weightUnit(request.getWeightUnit())
                .unitVolume(request.getUnitVolume())
                .volumeUnit(request.getVolumeUnit())
                .build();

        // save() on a brand new entity (no id) → Hibernate runs INSERT SQL
        OrderLinesMaster saved = orderLinesMasterRepository.save(line);

        return mapToResponseDTO(saved);
    }

    @Override
    public List<OrderLinesMasterResponse> getLinesByOrderId(Long orderId) {
        // Confirm the order exists first — gives a clean 404
        // instead of silently returning an empty list for a typo'd id
        orderMasterRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + orderId));

        return orderLinesMasterRepository.findByOrderMasterId(orderId)
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public OrderLinesMasterResponse getLineById(Long id) {
        OrderLinesMaster line = orderLinesMasterRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order line not found with id: " + id));

        return mapToResponseDTO(line);
    }

    @Override
    public OrderLinesMasterResponse updateLine(Long id, UpdateOrderLinesMasterRequest request) {

        // Step 1 — find the existing line first
        OrderLinesMaster existing = orderLinesMasterRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order line not found with id: " + id));

        // Step 2 — build a NEW object, but carry over the SAME id
        // AND the SAME orderMasterId — updating a line should never
        // accidentally move it to a different order or create a
        // duplicate row
        OrderLinesMaster updatedLine = OrderLinesMaster.builder()
                .id(existing.getId())
                .orderMaster(existing.getOrderMaster())
                .lineNumber(request.getLineNumber())
                .itemNumber(request.getItemNumber())
                .description(request.getDescription())
                .quantity(request.getQuantity())
                .uom(request.getUom())
                .unitPrice(request.getUnitPrice())
                .lineAmount(request.getLineAmount())
                .unitWeight(request.getUnitWeight())
                .weightUnit(request.getWeightUnit())
                .unitVolume(request.getUnitVolume())
                .volumeUnit(request.getVolumeUnit())
                .build();

        // save() on an entity WITH an existing id → Hibernate runs UPDATE SQL
        OrderLinesMaster saved = orderLinesMasterRepository.save(updatedLine);

        return mapToResponseDTO(saved);
    }

    @Override
    public void deleteLine(Long id) {
        OrderLinesMaster line = orderLinesMasterRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order line not found with id: " + id));

        orderLinesMasterRepository.delete(line);
    }

    // ─── PRIVATE HELPER ──────────────────────────────────────
    // Converts Entity → ResponseDTO using the builder pattern
    private OrderLinesMasterResponse mapToResponseDTO(OrderLinesMaster line) {
        return OrderLinesMasterResponse.builder()
                .id(line.getId())
                .orderMasterId(line.getOrderMaster().getId())
                .lineNumber(line.getLineNumber())
                .itemNumber(line.getItemNumber())
                .description(line.getDescription())
                .quantity(line.getQuantity())
                .uom(line.getUom())
                .unitPrice(line.getUnitPrice())
                .lineAmount(line.getLineAmount())
                .unitWeight(line.getUnitWeight())
                .weightUnit(line.getWeightUnit())
                .unitVolume(line.getUnitVolume())
                .volumeUnit(line.getVolumeUnit())
                .build();
    }
}
