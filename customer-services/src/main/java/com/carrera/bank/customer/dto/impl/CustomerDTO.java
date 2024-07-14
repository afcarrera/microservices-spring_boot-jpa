package com.carrera.bank.customer.dto.impl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.carrera.bank.customer.common.ValidationGroups;
import com.carrera.bank.customer.dto.IDTOEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
    @NotBlank(message = "Password cannot be blank", groups = ValidationGroups.Create.class)
    @Pattern(regexp = "^(?=.*[A-ZÑ])(?=.*[a-zñ])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,20}$",
            message = "Password must be at least 8 characters and maximum 20 characters and include an" +
                    " uppercase letter, a lowercase letter, a number, and a special character",
            groups = {ValidationGroups.Create.class, ValidationGroups.Update.class})
    private String password;
    private boolean status;
}
