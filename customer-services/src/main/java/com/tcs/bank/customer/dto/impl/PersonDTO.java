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
public class PersonDTO implements IDTOEntity {
    private String personId;
    private String name;
    private String gender;
    private int age;
    private String identification;
    private String address;
    private String phone;
}
