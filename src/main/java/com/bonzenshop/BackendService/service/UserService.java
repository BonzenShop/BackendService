package com.bonzenshop.BackendService.service;

import com.bonzenshop.BackendService.model.Account;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * Klasse, welche den UserDetailsService des Spring Security Frameworks implementiert.
 */
@Primary
@Service(value = "userService")
public class UserService implements UserDetailsService {

    /**
     * Liefert ein UserDetails-Objekt für den Benutzer der übergebenen E-Mail
     * @param username E-Mail des Benutzers, für den ein UserDetails-Objekt erstellt werden soll.
     * @return UserDetails-Objekt
     * @throws UsernameNotFoundException Falls kein Benutzer mit der übergebenen E-Mail existiert.
     */
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account user = DatabaseService.getAccountByEmail(username).get();
        if(user == null){
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), getAuthority(user));
    }

    /**
     * Erstellt aus der Rolle des übergebenen Benutzers ein Set von Authorities
     * @param user Benutzer, für den das Set erstellt werden soll
     * @return Set von Authorities
     */
    private Set getAuthority(Account user) {
        Set authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole()));
        return authorities;
    }
}
