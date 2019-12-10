package com.bonzenshop.BackendService.model;

/**
 * Datentyp, der benötigt wird, wenn ein Benutzer sich einloggen will
 */
public class AuthenticationRequest {
    /**
     * E-Mail des Benutzers
     */
    private String username;
    /**
     * Passwort des Benutzers
     */
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
