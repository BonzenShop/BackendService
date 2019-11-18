package com.bonzenshop.BackendService.controller;

import com.bonzenshop.BackendService.exception.EntityNotFoundException;
import com.bonzenshop.BackendService.model.Account;
import com.bonzenshop.BackendService.model.AuthenticationRequest;
import com.bonzenshop.BackendService.service.AuthenticationService;

import com.bonzenshop.BackendService.service.DatabaseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin()
@RestController
@RequestMapping
public class AuthenticationController {

    private AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<Account> loginUser(@RequestBody AuthenticationRequest request) {
        return new ResponseEntity<>(authenticationService.generateJWTToken(request.getUsername(), request.getPassword()), HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<Account> createUser(@RequestBody Account account) {
        if(DatabaseService.emailAlreadyTaken(account.getEmail(), 0)){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }else{
            DatabaseService.createAccount(account);
            return new ResponseEntity<>(authenticationService.generateJWTToken(account.getEmail(), account.getPassword()), HttpStatus.OK);
        }
    }

    @PostMapping("/updateUser")
    public ResponseEntity<Account> updateUser(@RequestBody Account account) {
        if(DatabaseService.emailAlreadyTaken(account.getEmail(), account.getId())){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }else{
            int rowsAffected = DatabaseService.updateAccount(account);
            if(rowsAffected > 0){
                return new ResponseEntity<>(authenticationService.generateJWTToken(account.getId(), account.getEmail()), HttpStatus.OK);
            }else{
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }

    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
}
