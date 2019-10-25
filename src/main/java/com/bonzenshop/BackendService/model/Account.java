package com.bonzenshop.BackendService.model;

public class Account {
    private int id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String birthDate;
    private String role;
    private String token;

    public Account(int id, String email, String password, String firstName, String lastName, String birthDate, String role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.role = role;
        this.token = null;
    }

    public Account(int id, String email, String firstName, String lastName, String birthDate, String role) {
        this.id = id;
        this.email = email;
        this.password = null;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.role = role;
        this.token = null;
    }

    public Account(Account account, String token){
        this.id = account.id;
        this.email = account.email;
        this.password = null;
        this.firstName = account.firstName;
        this.lastName = account.lastName;
        this.birthDate = account.birthDate;
        this.role = account.role;
        this.token = token;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
