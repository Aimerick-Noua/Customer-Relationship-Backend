package com.example.crm.payload.Request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserInformation {
    private String firstname;
    private String lastname;
    private String phone;
    private String address;
    private String profilePicture;
}
