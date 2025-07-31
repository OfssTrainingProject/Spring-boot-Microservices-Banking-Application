package com.useraccountservice.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String mobileNumber;
    private String aadharNumber;
    private String address;
    private String state;
    private String pincode;
}
