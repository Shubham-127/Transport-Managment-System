package com.example.TMS.integration.jde.PersistenceService;

import com.example.TMS.integration.jde.dto.JdeItem;
import com.example.TMS.model.ItemMaster;
import com.example.TMS.repository.ItemMasterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service@RequiredArgsConstructor
public class JdeItemPersistenceService {
    private final ItemMasterRepository itemMasterRepository;


    public void saveItemMaster(List<JdeItem> jdeItems){
        for(JdeItem jdeItem: jdeItems){
            try{
                saveOrUpdateItem(jdeItem);
            }catch (Exception e){
                log.error("Failed to save item {}: {}", jdeItem.getItemNumber(), e.getMessage(),e);
            }
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveOrUpdateItem(JdeItem jdeItem){
        ItemMaster item = itemMasterRepository
                .findByItemNumber(jdeItem.getItemNumber())
                .orElseGet(ItemMaster::new);
        item.setItemNumber(safe(jdeItem.getItemNumber()));
        item.setShortItemNo(Long.valueOf(safe(String.valueOf(jdeItem.getShortItemNo()))));
        item.setDescription(safe(jdeItem.getDescription()));
        item.setDescription2(safe(jdeItem.getDescription2()));
        item.setThirdItemNumber(safe(jdeItem.getThirdItemNumber()));
        item.setLineType(safe(jdeItem.getLineType()));
        item.setStockingType(safe(jdeItem.getStockingType()));

        ItemMaster savedItem = itemMasterRepository.save(item);

        log.info("Saved Item {}", savedItem.getItemNumber());



    }
    private String safe(String value){
        return(value==null|| value.isBlank())? "N/A": value;
    }



}

