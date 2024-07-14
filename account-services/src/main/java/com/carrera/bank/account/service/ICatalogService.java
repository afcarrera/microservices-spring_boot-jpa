package com.carrera.bank.account.service;

import com.carrera.bank.account.dto.impl.AccountDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

/**
 * Interface for Catalog Service.
 * Defines the operations for catalogs.
 *
 * @author acarrera
 * @version 1.0
 */
public interface ICatalogService {

    /**
     * Get a map of catalogs.
     *
     * @param accountTypeCode the account type catalog
     * @param accountValueCode the account value catalog
     * @return the map
     */
    Map<String, String> getCatalogValueByTypeAndValue(String accountTypeCode, String accountValueCode);
}