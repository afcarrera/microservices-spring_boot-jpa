package com.carrera.bank.account.connector;

import com.carrera.bank.account.dto.external.Catalog;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * Interface for Catalog Connector Service.
 *
 * @author acarrera
 * @version 1.0
 */
@FeignClient(name = "catalog-services")
public interface ICatalogConnector {
    @PostMapping("catalog-services/api/v1/values/filtered")
    List<Catalog> findByTypeAndValue(@RequestBody Catalog catalog);
}
