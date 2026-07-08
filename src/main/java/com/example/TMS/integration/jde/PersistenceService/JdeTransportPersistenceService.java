package com.example.TMS.integration.jde.PersistenceService;

import com.example.TMS.integration.jde.dto.JdeTransportMaster;
import com.example.TMS.integration.jde.dto.JdeVehicle;
import com.example.TMS.model.TransportMaster;
import com.example.TMS.model.VehicleMaster;
import com.example.TMS.repository.TransportMasterRepository;
import com.example.TMS.repository.VehicleMasterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class JdeTransportPersistenceService {

    private final TransportMasterRepository transportMasterRepository;
    private final VehicleMasterRepository vehicleMasterRepository;

    // ★ NO @Transactional here — each transport gets its own transaction below
    public void saveTransportMasters(List<JdeTransportMaster> jdeTransports) {
        for (JdeTransportMaster jdeTransport : jdeTransports) {
            try {
                saveOrUpdateTransport(jdeTransport);
            } catch (Exception e) {
                log.error("Failed to save transport {}: {}",
                        jdeTransport.getCompanyName(), e.getMessage(), e);
            }
        }
    }

    // ★ REQUIRES_NEW = each transport gets its own fresh transaction
    // If one fails, only THAT transport rolls back, not all 21
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveOrUpdateTransport(JdeTransportMaster jdeTransport) {
        TransportMaster transport = transportMasterRepository
                .findByAddressNumber(jdeTransport.getAddressNumber())
                .orElseGet(TransportMaster::new);

        transport.setCompanyName(safe(jdeTransport.getCompanyName()));
        transport.setGstNumber(safe(jdeTransport.getGstNumber()));
        transport.setPanNumber(safe(jdeTransport.getPanNumber()));
        transport.setContactPerson(safe(jdeTransport.getContactPerson()));
        transport.setMobile(safe(jdeTransport.getMobile()));
        transport.setEmail(safe(jdeTransport.getEmail()));
        transport.setAddress(safe(jdeTransport.getAddress()));
        transport.setCity(safe(jdeTransport.getCity()));
        transport.setState(safe(jdeTransport.getState()));
        transport.setPincode(safe(jdeTransport.getPincode()));
        transport.setActive(safe(jdeTransport.getActive()));
        transport.setRemarks(jdeTransport.getRemarks());
        transport.setAddressNumber(jdeTransport.getAddressNumber());

        TransportMaster savedTransport = transportMasterRepository.save(transport);

        if (jdeTransport.getVehicles() != null) {
            for (JdeVehicle jdeVehicle : jdeTransport.getVehicles()) {
                try {
                    saveOrUpdateVehicle(jdeVehicle, savedTransport);
                } catch (Exception e) {
                    log.error("Failed to save vehicle {} for transport {}: {}",
                            jdeVehicle.getVehicleNumber(),
                            savedTransport.getCompanyName(),
                            e.getMessage());
                }
            }
        }

        log.info("Saved transport {} ({} vehicles)",
                savedTransport.getCompanyName(),
                jdeTransport.getVehicles() != null ? jdeTransport.getVehicles().size() : 0);
    }

    private void saveOrUpdateVehicle(JdeVehicle jdeVehicle, TransportMaster transport) {
        // ★ Skip vehicles with no vehicle number — nothing to upsert on
        if (jdeVehicle.getVehicleNumber() == null || jdeVehicle.getVehicleNumber().isBlank()) {
            log.warn("Skipping vehicle with null vehicleNumber for transport {}",
                    transport.getCompanyName());
            return;
        }

        VehicleMaster vehicle = vehicleMasterRepository
                .findByVehicleNumber(jdeVehicle.getVehicleNumber())
                .orElseGet(VehicleMaster::new);

        vehicle.setTransportMaster(transport);
        vehicle.setVehicleNumber(jdeVehicle.getVehicleNumber());

        // ★ safe defaults for nullable fields that VehicleMaster marks NOT NULL
        vehicle.setVehicleType(safe(jdeVehicle.getVehicleType()));
        vehicle.setMaxWeightCapacity(jdeVehicle.getMaxWeightCapacity() != null
                ? jdeVehicle.getMaxWeightCapacity() : 0);
        vehicle.setMaxVolumeCapacity(jdeVehicle.getMaxVolumeCapacity() != null
                ? jdeVehicle.getMaxVolumeCapacity() : 0);
        vehicle.setStatus(safe(jdeVehicle.getStatus()));
        vehicle.setGpsDeviceId(jdeVehicle.getGpsDeviceId());
        vehicle.setTotalWeight(jdeVehicle.getTotalWeight());
        vehicle.setTotalVolume(jdeVehicle.getTotalVolume());

        vehicleMasterRepository.save(vehicle);
    }

    private String safe(String value) {
        return (value == null || value.isBlank()) ? "N/A" : value;
    }
}