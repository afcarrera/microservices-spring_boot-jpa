package com.carrera.bank.customer.dto.impl;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.carrera.bank.customer.common.Constants;
import com.carrera.bank.customer.common.ValidationGroups;
import com.carrera.bank.customer.dto.IDTOEntity;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersonDTO implements IDTOEntity {
    private String personId;
    @NotBlank(message = Constants.NOT_BLANK, groups = ValidationGroups.Create.class)
    @Pattern(regexp = "^[A-Za-zÑn]+( [A-Za-zÑñ]+)*$",
            message = "Name must contain only letters and single spaces between words",
            groups = {ValidationGroups.Create.class, ValidationGroups.Update.class})
    private String name;
    @NotBlank(message = Constants.NOT_BLANK, groups = ValidationGroups.Create.class)
    @Size(min= 3, max = 5, message = Constants.CATALOG_LENGTH,
            groups = {ValidationGroups.Create.class, ValidationGroups.Update.class})
    private String genderTypeCode;
    @NotBlank(message = Constants.NOT_BLANK, groups = ValidationGroups.Create.class)
    @Size(min= 3, max = 5, message = Constants.CATALOG_LENGTH,
            groups = {ValidationGroups.Create.class, ValidationGroups.Update.class})
    private String genderValueCode;
    @Min(value = 0, message = "Should not be less than 0",
            groups = {ValidationGroups.Create.class, ValidationGroups.Update.class})
    @Max(value = 255, message = "Should not be greater than 255",
            groups = {ValidationGroups.Create.class, ValidationGroups.Update.class})
    private short age;
    @Pattern(regexp = "^\\d{10}$", message = "Should be a 10-digit number",
            groups = {ValidationGroups.Create.class, ValidationGroups.Update.class})
    @NotBlank(message = Constants.NOT_BLANK, groups = ValidationGroups.Create.class)
    private String identification;
    @NotBlank(message = Constants.NOT_BLANK, groups = ValidationGroups.Create.class)
    @Pattern(regexp = "^[A-Za-zÑñ0-9\\s,.-]+$", message = "Invalid address format",
            groups = {ValidationGroups.Create.class, ValidationGroups.Update.class})
    @Size(max = 175, message = "Password must be between 8 and 20 characters",
            groups = {ValidationGroups.Create.class, ValidationGroups.Update.class})
    private String address;
    @Pattern(regexp = "^\\d{10}$", message = "Should be a 10-digit number",
            groups = {ValidationGroups.Create.class, ValidationGroups.Update.class})
    @NotBlank(message = Constants.NOT_BLANK, groups = ValidationGroups.Create.class)
    private String phone;
}
