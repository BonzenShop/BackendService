package com.bonzenshop.BackendService.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Account {
    private int id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String birthDate;
    private String role;
    private String country;
    private String city;
    private String postalCode;
    private String street;
    private String token;

    @JsonCreator
    public Account(@JsonProperty("email") String email,
                   @JsonProperty("password") String password,
                   @JsonProperty("firstName") String firstName,
                   @JsonProperty("lastName") String lastName,
                   @JsonProperty("birthDate") String birthDate,
                   @JsonProperty("country") String country,
                   @JsonProperty("city") String city,
                   @JsonProperty("postalCode") String postalCode,
                   @JsonProperty("street") String street)
    {
        this.id = 0;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.country = country;
        this.city = city;
        this.postalCode = postalCode;
        this.street = street;
        this.role = "Kunde";
        this.token = null;
    }

    public Account(int id,
                   String email,
                   String password,
                   String firstName,
                   String lastName,
                   String birthDate,
                   String role,
                   String country,
                   String city,
                   String postalCode,
                   String street) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.role = role;
        this.country = country;
        this.city = city;
        this.postalCode = postalCode;
        this.street = street;
        this.token = null;
    }

    public Account(int id,
                   String email,
                   String firstName,
                   String lastName,
                   String birthDate,
                   String role,
                   String country,
                   String city,
                   String postalCode,
                   String street) {
        this.id = id;
        this.email = email;
        this.password = null;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.role = role;
        this.country = country;
        this.city = city;
        this.postalCode = postalCode;
        this.street = street;
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
        this.country = account.country;
        this.city = account.city;
        this.postalCode = account.postalCode;
        this.street = account.street;
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
