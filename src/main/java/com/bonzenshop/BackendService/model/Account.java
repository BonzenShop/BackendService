package com.bonzenshop.BackendService.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Die Klasse Account stellt ein Benutzerkonto dar.
 */
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

    /**
     * Dieser Konstruktor wird verwendet wenn ein Account als JSON von einem Endpunkt empfangen wird.
     * Die Id wird auf 0 gesetzt, die Rolle auf Kunde und der Token auf null. Alle anderen Attribute werden übernommen.
     * Dieser wird im {@link com.bonzenshop.BackendService.controller.AuthenticationController} verwendet.
     * @param email Die E-Mail des Benutzers
     * @param password Das Passwort des Benutzers
     * @param firstName Der Vorname des Benutzers
     * @param lastName Der Nachname des Benutzers
     * @param birthDate Das Geburtsdatum des Benutzers
     * @param country Das Land der Adresse des Benutzers
     * @param city Die Stadt der Adresse des Benutzers
     * @param postalCode Die PLZ/ZIP der Adresse des Benutzers
     * @param street Die Straße der Adresse des Benutzers
     */
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

    /**
     * Dieser Konstruktor wird verwendet, wenn ein Benutzerkonto von der Datenbank geladen wird.
     * Deshalb werden hier alle Attribute, bis auf den Token initialisiert, welcher auf null gesetzt wird.
     * @param id Die Id des Benutzers
     * @param email Die E-Mail des Benutzers
     * @param password Das Passwort des Benutzers
     * @param firstName Der Vorname des Benutzers
     * @param lastName Der Nachname des Benutzers
     * @param birthDate Das Geburtsdatum des Benutzers
     * @param role Die Rolle des Benutzers
     * @param country Das Land der Adresse des Benutzers
     * @param city Die Stadt der Adresse des Benutzers
     * @param postalCode Die PLZ/ZIP der Adresse des Benutzers
     * @param street Die Straße der Adresse des Benutzers
     */
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
    /**
     * Dieser Konstruktor wird verwendet, wenn ein Benutzerkonto von der Datenbank geladen wird.
     * Hier werden alle Attribute, bis auf den Token und das Password initialisiert. Die werden beide auf null gesetzt.
     * @param id Die Id des Benutzers
     * @param email Die E-Mail des Benutzers
     * @param firstName Der Vorname des Benutzers
     * @param lastName Der Nachname des Benutzers
     * @param birthDate Das Geburtsdatum des Benutzers
     * @param role Die Rolle des Benutzers
     * @param country Das Land der Adresse des Benutzers
     * @param city Die Stadt der Adresse des Benutzers
     * @param postalCode Die PLZ/ZIP der Adresse des Benutzers
     * @param street Die Straße der Adresse des Benutzers
     */
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

    /**
     * Dieser Konstruktor wird verwendet, wenn einem Account ein Token hinzugefügt werden muss.
     * @param account Alle Attribute des übergebenen Accounts werden übernommmen, bis auf den Token.
     * @param token Der Token, welcher dem Account hinzugefügt werden soll.
     */
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
