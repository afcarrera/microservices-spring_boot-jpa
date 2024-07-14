package com.carrera.bank.account.dto.impl;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.carrera.bank.account.common.Constants;
import com.carrera.bank.account.common.ValidationGroups;
import com.carrera.bank.account.dto.IDTOEntity;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.apache.tomcat.util.bcel.Const;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountDTO implements IDTOEntity {
    @JsonProperty(Constants.ID_LABEL)
    private String accountId;
    @NotBlank(message = Constants.NOT_BLANK, groups = {ValidationGroups.Create.class, ValidationGroups.Update.class})
    @Size(max = 36, message = Constants.UUID_LENGTH,
            groups = {ValidationGroups.Create.class, ValidationGroups.Update.class})
    private String customerId;
    @Pattern(regexp = "^\\d{10}$", message = "Should be a 10-digit number",
            groups = {ValidationGroups.Create.class, ValidationGroups.Update.class})
    @NotBlank(message = Constants.NOT_BLANK, groups = {ValidationGroups.Create.class, ValidationGroups.Update.class})
    private String accountNumber;
    @NotBlank(message = Constants.NOT_BLANK, groups = {ValidationGroups.Create.class, ValidationGroups.Update.class})
    @Size(min= 3, max = 5, message = Constants.CATALOG_LENGTH,
            groups = {ValidationGroups.Create.class, ValidationGroups.Update.class})
    private String accountTypeCode;
    @NotBlank(message = Constants.NOT_BLANK, groups = {ValidationGroups.Create.class, ValidationGroups.Update.class})
    @Size(min= 3, max = 5, message = Constants.CATALOG_LENGTH,
            groups = {ValidationGroups.Create.class, ValidationGroups.Update.class})
    private String accountValueCode;
    private String accountTypeValue;
    @Digits(integer = 18, fraction = 2, message = Constants.BIG_DECIMAL_VALIDATION,
            groups = {ValidationGroups.Create.class, ValidationGroups.Update.class})
    private BigDecimal initialBalance;
    private boolean status;
}