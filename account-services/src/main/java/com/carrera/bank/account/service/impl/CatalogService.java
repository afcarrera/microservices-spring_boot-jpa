package com.carrera.bank.account.service.impl;

import com.carrera.bank.account.connector.ICatalogConnector;
import com.carrera.bank.account.dto.external.Catalog;
import com.carrera.bank.account.service.ICatalogService;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Transaction service implementation.
 *
 * @author acarrera
 * @version 1.0
 */
@Slf4j
@Service
public class CatalogService implements ICatalogService {

    @Autowired
    private ICatalogConnector iCatalogConnector;

    public Map<String, String> getCatalogValueByTypeAndValue(String accountTypeCode, String accountValueCode){
        List<Catalog> catalogList = new ArrayList<>();
        try {
            catalogList = this.iCatalogConnector.findByTypeAndValue(Catalog.builder()
                    .code(accountValueCode)
                    .typeCode(accountTypeCode).build());
        }catch (FeignException ex){
            log.error(ex.getMessage());
        }
        if (!catalogList.isEmpty()){
            return catalogList.stream()
                    .collect(Collectors.toMap(Catalog::getCode, Catalog::getValue));
        }
        return new ConcurrentHashMap<>();
    }
}
