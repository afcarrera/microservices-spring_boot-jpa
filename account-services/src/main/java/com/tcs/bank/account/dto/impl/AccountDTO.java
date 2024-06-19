package com.tcs.bank.account.dto.impl;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tcs.bank.account.common.Constants;
import com.tcs.bank.account.dto.IDTOEntity;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountDTO implements IDTOEntity {
    @JsonProperty(Constants.ID_LABEL)
    private String accountId;
    private String customerId;
    private String accountNumber;
    private String accountType;
    private Double initialBalance;
    private boolean status;
}