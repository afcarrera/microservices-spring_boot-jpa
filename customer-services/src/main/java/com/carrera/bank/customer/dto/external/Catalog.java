package com.carrera.bank.customer.dto.external;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Catalog {
    private String code;
    private String value;
    private String typeCode;
}
