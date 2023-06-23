package com.store.store.Model.Entities.dto;

import com.store.store.Model.Entities.Role;

import javax.persistence.*;
import java.util.Set;

public class CustomerDto {

    private String username;
    private String password;
    private String email;
    private String phoneNumber;



    // Getters and setters


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
