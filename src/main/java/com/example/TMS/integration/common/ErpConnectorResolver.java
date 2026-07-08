package com.example.TMS.integration.common;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;



@Component
@RequiredArgsConstructor
public class ErpConnectorResolver {

    private final Map<String, ErpConnector> connectors;

    public ErpConnector resolve(ErpType type){
        return switch (type){
            case JDE -> connectors.get("jdeConnector");
            case SAP -> connectors.get("sapConnector");
        };
    }
}
