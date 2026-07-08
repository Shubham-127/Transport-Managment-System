package com.example.TMS.integration.jde.PersistenceService;


import com.example.TMS.integration.jde.dto.JdeCustomer;
import com.example.TMS.model.CustomerMaster;
import com.example.TMS.repository.CustomerMasterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class JdeCustomerPersistenceService {

    private final CustomerMasterRepository customerMasterRepository;

    public void saveCustomerMaster(List<JdeCustomer> jdeCustomers) {

        for (JdeCustomer jdeCustomer : jdeCustomers) {

            try {
                saveOrUpdateCustomer(jdeCustomer);
            } catch (Exception e) {
                log.error("Failed to save customer {} : {}",
                        jdeCustomer.getAddressNumber(),
                        e.getMessage(),
                        e);
            }
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveOrUpdateCustomer(JdeCustomer jdeCustomer) {

        CustomerMaster customer = customerMasterRepository
                .findByCustomerId(Long.valueOf(jdeCustomer.getAddressNumber()))
                .orElseGet(CustomerMaster::new);

        customer.setCustomerId(Long.valueOf(jdeCustomer.getAddressNumber()));
        customer.setCustomerName(safe(jdeCustomer.getCustomerName()));
        customer.setGstNumber(safe(jdeCustomer.getGstNumber()));
        customer.setPanNumber(safe(jdeCustomer.getPanNumber()));
        customer.setContactPerson(safe(jdeCustomer.getContactPerson()));
        customer.setMobile(safe(jdeCustomer.getMobile()));
        customer.setEmail(safe(jdeCustomer.getEmail()));
        customer.setAddress(safe(jdeCustomer.getAddress()));
        customer.setCity(safe(jdeCustomer.getCity()));
        customer.setState(safe(jdeCustomer.getState()));
        customer.setPincode(safe(jdeCustomer.getPinCode()));
        customer.setCountry(safe(jdeCustomer.getCountry()));
        customer.setActive(safe(jdeCustomer.getActive()));
        customer.setRemarks(safe(jdeCustomer.getRemarks()));

        CustomerMaster savedCustomer = customerMasterRepository.save(customer);

        log.info("Saved Customer {}", savedCustomer.getCustomerName());
    }

    private String safe(String value) {
        return (value == null || value.isBlank()) ? "N/A" : value;
    }

}
