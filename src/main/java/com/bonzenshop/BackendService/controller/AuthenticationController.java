package com.bonzenshop.BackendService.controller;

import com.bonzenshop.BackendService.model.Account;
import com.bonzenshop.BackendService.model.AuthenticationRequest;
import com.bonzenshop.BackendService.service.AuthenticationService;

import com.bonzenshop.BackendService.service.DatabaseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * Controller, welcher alle Endpunkte beinhaltet, welche die Generierung eines JWTs beinhalten.
 */
@CrossOrigin()
@RestController
@RequestMapping
public class AuthenticationController {

    public AuthenticationController() {
    }

    /**
     * Endpunkt zum Einloggen eines Benutzers.
     * @param request Beinhaltet Benutzername und Passwort, welche der Benutzer eingegeben hat.
     * @return HTTP-Response mit dem gesamten Benutzerkonto (außer Passwort) und dem generierten JWT im Body.
     */
    @PostMapping("/login")
    public ResponseEntity<Account> loginUser(@RequestBody AuthenticationRequest request) {
        return new ResponseEntity<>(AuthenticationService.generateJWTToken(request.getUsername(), request.getPassword()), HttpStatus.OK);
    }

    /**
     * Endpunkt zum Registrieren beim Webshop.
     * @param account Die Kontoinformationen, welche bei der Registrierung eingegeben wurden.
     * @return HTTP-Response mit dem gesamten Benutzerkonto (außer Passwort) und dem generierten JWT im Body. Status-Code 400 falls die E-Mail bereits vergeben ist.
     */
    @PostMapping("/signup")
    public ResponseEntity<Account> createUser(@RequestBody Account account) {
        if(DatabaseService.isEmailAlreadyTaken(account.getEmail(), 0)){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }else{
            DatabaseService.createAccount(account);
            return new ResponseEntity<>(AuthenticationService.generateJWTToken(account.getEmail(), account.getPassword()), HttpStatus.OK);
        }
    }

    /**
     * Endpunkt zum Ändern der eigenen Kontoinforamtionen.
     * @param account Die neuen Kontoinformationen.
     * @return HTTP-Response mit dem gesamten Benutzerkonto (außer Passwort) und dem neu generierten JWT im Body. Status-Code 400 falls die E-Mail bereits vergeben ist oder der Datenbankarufruf fehlschlägt.
     */
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/updateUser")
    public ResponseEntity<Account> updateUser(@RequestBody Account account) {
        if(DatabaseService.isEmailAlreadyTaken(account.getEmail(), account.getId())){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }else{
            int rowsAffected = DatabaseService.updateAccount(account);
            if(rowsAffected > 0){
                return new ResponseEntity<>(AuthenticationService.generateJWTToken(account.getId(), account.getEmail()), HttpStatus.OK);
            }else{
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }

    }
}
