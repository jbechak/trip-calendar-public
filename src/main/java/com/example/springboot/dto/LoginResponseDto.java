package com.example.springboot.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.example.springboot.model.UserModel;

/*
    The acronym DTO is being used for "data transfer object". It means that this type of class is specifically
    created to transfer data between the client and the server. For example, CredentialsDto represents the data a client must
    pass to the server for a login endpoint, and TokenDto represents the object that's returned from the server
    to the client from a login endpoint.
 */
public class LoginResponseDto {

    private String token;
    private UserModel user;

    public LoginResponseDto(String token, UserModel user) {
        this.token = token;
        this.user = user;
    }

    @JsonProperty("token")
    String getToken() {
        return token;
    }

    void setToken(String token) {
        this.token = token;
    }

    @JsonProperty("user")
    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }
}
