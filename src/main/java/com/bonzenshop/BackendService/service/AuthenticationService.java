package com.bonzenshop.BackendService.service;

import com.bonzenshop.BackendService.exception.EntityNotFoundException;
import com.bonzenshop.BackendService.model.Account;
import org.springframework.stereotype.Service;

/**
 * Service zum Generieren eines JWT.
 */
@Service
public class AuthenticationService {

    /**
     * Generiert einen JSON-Web-Token, falls E-Mail und Passwort korrekt sind.
     * @param email E-Mail des Benutzers
     * @param password Passwort des Benutzers
     * @return Gesamte Kontoinformationen des Benutzers inklusive generiertem JWT, falls E-Mail und Passwort korrekt sind. Ansonsten EntityNotFoundException.
     */
    public static Account generateJWTToken(String email, String password) {
        return DatabaseService.getAccountByEmail(email)
                .filter(account -> password.equals(account.getPassword()))
                .map(account -> new Account(account, JwtTokenService.generateToken(email, account.getRole())))
                .orElseThrow(() ->  new EntityNotFoundException("Account not found"));
    }

    /**
     * Generiert einen JSON-Web-Token
     * @param id Die ID des Benutzers, für den ein JWT generiert werden soll.
     * @param email Die E-Mail des Benutzers, für den ein JWT generiert werden soll.
     * @return Gesamte Kontoinformationen des Benutzers inklusive generiertem JWT. Bei Fehler EntityNotFoundException.
     */
    public static Account generateJWTToken(int id, String email) {
        return DatabaseService.getAccountById(id)
                .map(account -> new Account(account, JwtTokenService.generateToken(email, account.getRole())))
                .orElseThrow(() ->  new EntityNotFoundException("Account not found"));
    }
}
