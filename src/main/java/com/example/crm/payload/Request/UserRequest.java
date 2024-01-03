package com.example.crm.payload.Request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {
    private Long id;
    private String firstname;
    private String lastname;
    private String address;
    private String phone;
}
