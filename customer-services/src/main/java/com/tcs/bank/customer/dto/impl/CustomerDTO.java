package com.tcs.bank.customer.dto.impl;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tcs.bank.customer.dto.IDTOEntity;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerDTO extends PersonDTO implements IDTOEntity {
    private String customerId;
    @NotBlank(message = "Password cannot be blank")
    private String password;
    @NotBlank(message = "Status cannot be blank")
    private boolean status;
}
