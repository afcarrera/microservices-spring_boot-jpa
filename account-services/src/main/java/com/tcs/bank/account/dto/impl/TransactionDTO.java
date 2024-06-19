package com.tcs.bank.account.dto.impl;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tcs.bank.account.common.Constants;
import com.tcs.bank.account.dto.IDTOEntity;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionDTO implements IDTOEntity {
    @JsonProperty(Constants.ID_LABEL)
    private String transactionId;
    private String accountId;
    private LocalDateTime date;
    private String transactionType;
    private Double amount;
    private Double balance;
}