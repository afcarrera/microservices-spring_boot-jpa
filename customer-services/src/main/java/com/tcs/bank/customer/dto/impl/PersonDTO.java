package com.tcs.bank.customer.dto.impl;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tcs.bank.customer.dto.IDTOEntity;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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
    @NotBlank(message = "Name cannot be blank")
    private String name;
    @NotBlank(message = "Gender cannot be blank")
    private String gender;
    @Min(value = 0, message = "Age should not be less than 0")
    @Max(value = 255, message = "Age should not be greater than 255")
    private short age;
    @Pattern(regexp = "\\d{10}", message = "Identification should be a 10-digit number")
    private String identification;
    @NotBlank(message = "Address cannot be blank")
    private String address;
    @NotBlank(message = "Phone cannot be blank")
    @Pattern(regexp = "\\d{10}", message = "Phone should be a 10-digit number")
    private String phone;
}
