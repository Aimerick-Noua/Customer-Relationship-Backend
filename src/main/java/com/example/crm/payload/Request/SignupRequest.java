package com.example.crm.payload.Request;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;


@Getter
@Setter
public class SignupRequest {
    private String firstname;
    private String lastname;
    private String phone;
    private String address;
    private String email;
    private String password;
    private Set<String> roles;

}