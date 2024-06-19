package com.tcs.bank.account.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Customer {
    private String id;
    private String personId;
    private String password;
    private boolean status;
    private Person person;
}
