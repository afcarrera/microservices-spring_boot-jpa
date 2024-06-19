package com.tcs.bank.account.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Person {
    private String id;
    private String name;
    private String gender;
    private int age;
    private String identification;
    private String address;
    private String phone;
}
