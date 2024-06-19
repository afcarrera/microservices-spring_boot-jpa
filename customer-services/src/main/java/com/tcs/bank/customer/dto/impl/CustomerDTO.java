package com.tcs.bank.customer.dto.impl;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tcs.bank.customer.common.Constants;
import com.tcs.bank.customer.dto.IDTOEntity;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerDTO implements IDTOEntity {
    @JsonProperty(Constants.ID_LABEL)
    private String customerId;
    private String personId;
    private String password;
    private boolean status;
    private PersonDTO person;
}
