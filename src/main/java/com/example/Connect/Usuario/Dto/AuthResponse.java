
package com.example.forutec2.Usuario.Dto;

public class AuthResponse {
    private String jwt;

    public AuthResponse(String jwt) {
        this.jwt = jwt;
    }


    public String getToken() {
        return jwt;
    }

    public void setToken(String token) {
        this.jwt = token;
    }
}
