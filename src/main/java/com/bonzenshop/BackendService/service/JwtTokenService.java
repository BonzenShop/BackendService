package com.bonzenshop.BackendService.service;

import io.jsonwebtoken.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Service, welcher Methoden bereitstellt, um mit JWTs zu arbeiten.
 */
@Component
public class JwtTokenService {

    /**
     * Schlüssel, unter dem die Rolle des Benutzers im JWT gespeichert ist.
     */
    private static final String AUTHORITIES_KEY = "authority";
    /**
     * Schlüssel, mit welchem der JWT verschlüsselt wird.
     */
    private static String secret = "mySecret";
    /**
     * Zeit in ms, für welche ein JWT gültig ist.
     */
    private static int expiration = 86400000; //86400000ms = 24h

    /**
     * Methode zur Generierung eines JWT
     * @param username E-Mail des Benutzers, für den der JWT generiert werden soll.
     * @param role Rolle des Benutzers, für den der JWT generiert werden soll.
     * @return JWT als String.
     */
    public static String generateToken(String username, String role) {
        final Date createdDate = new Date();
        final Date expirationDate = calculateExpirationDate(createdDate);
        return Jwts.builder()
                .setClaims(new HashMap<>())
                .setSubject(username)
                .setIssuedAt(createdDate)
                .setExpiration(expirationDate)
                .claim(AUTHORITIES_KEY, "ROLE_"+role)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    /**
     * Errechnet das Ablaufdatum des JWT auf Basis des Erstelldatums und der Laufzeit (expiration)
     * @param createdDate Datum, an dem der Token erstellt wurde.
     * @return Datum, an dem der Token abläuft
     */
    private static Date calculateExpirationDate(Date createdDate) {
        return new Date(createdDate.getTime() + expiration);
    }

    /**
     * Liefert den Benutzernamen des übergebenen Tokens
     * @param token Token, aus welchem der Benutzername ausgelesen werden soll.
     * @return Benutzername im Token
     */
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    /**
     * Liefert das Ablaufdatum des übergebenen Tokens
     * @param token Token, aus welchem das Ablaufdatum ausgelesen werden soll.
     * @return Datum, an dem der Token abläuft.
     */
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Ob der Token abgelaufen ist.
     * @param token Token, der zu überprüfen ist.
     * @return true, falls der Token noch gültig ist. Ansonsten false.
     */
    private Boolean isTokenNotExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.after(new Date());
    }

    /**
     * Überprüft, ob der übergebene Token gültig ist.
     * @param token Token, der zu validieren ist.
     * @param userDetails UserDetail, mit denen der Token verglichen wird.
     * @return true in einem Optional, falls der Token valide ist. Ansonsten false in einem Optional
     */
    public Optional<Boolean> validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return Optional.of(username.equals(userDetails.getUsername()) && isTokenNotExpired(token));
    }

    /**
     * Erstellt aus dem JWT und den UserDetails den AuthenticationToken für das Spring Security Framework.
     * @param token JWT
     * @param userDetails UserDetails
     * @return AuthenticationToken für das Spring Security Framework
     */
    public UsernamePasswordAuthenticationToken getAuthentication(final String token, final UserDetails userDetails) {

        final JwtParser jwtParser = Jwts.parser().setSigningKey(secret);

        final Jws<Claims> claimsJws = jwtParser.parseClaimsJws(token);

        final Claims claims = claimsJws.getBody();

        final Collection authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        return new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
    }
}
