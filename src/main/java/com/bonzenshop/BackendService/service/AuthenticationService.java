package com.bonzenshop.BackendService.service;

import com.bonzenshop.BackendService.exception.EntityNotFoundException;
import com.bonzenshop.BackendService.model.Account;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    public Account generateJWTToken(String email, String password) {
        return DatabaseService.getAccountByEmail(email)
                .filter(account -> password.equals(account.getPassword()))
                .map(account -> new Account(account, JwtTokenService.generateToken(email, account.getRole())))
                .orElseThrow(() ->  new EntityNotFoundException("Account not found"));
    }

    public Account generateJWTToken(int id, String email) {
        return DatabaseService.getAccountById(id)
                .map(account -> new Account(account, JwtTokenService.generateToken(email, account.getRole())))
                .orElseThrow(() ->  new EntityNotFoundException("Account not found"));
    }
}
