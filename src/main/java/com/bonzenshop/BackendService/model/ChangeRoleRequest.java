package com.bonzenshop.BackendService.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Das Objekt, welches verwendet wird, wenn die Rolle eines Benutzerkontos geändert werden soll.
 */
public class ChangeRoleRequest {
    /**
     * Die ID des Benutzerkontos, dessen Rolle geändert werden soll.
     */
    private int user;
    /**
     * Die Rolle, die der Benutzer erhalten soll.
     */
    private String role;

    /**
     * Der Konstruktor für den Endpunkt. Alle Attribute werden als Parameter übergeben und übernommen.
     * @param user Die Benutzer-ID
     * @param role Die neue Rolle
     */
    @JsonCreator
    public ChangeRoleRequest(@JsonProperty int user, @JsonProperty String role) {
        this.user = user;
        this.role = role;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
