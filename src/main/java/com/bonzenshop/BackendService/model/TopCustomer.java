package com.bonzenshop.BackendService.model;

/**
 * Top Kunde des Webshops. Wird für die Auflistung der top Kunden benötigt.
 */
public class TopCustomer {
    /**
     * Vorname des Kunden
     */
    String firstName;
    /**
     * Nachname des Kunden
     */
    String lastName;
    /**
     * Gesamteinkaufswert des Kunden
     */
    double totalPurchase;

    /**
     * Alle Attribute werden übergeben
     * @param firstName Vorname
     * @param lastName Nachname
     * @param totalPurchase Gesamteinkaufswert
     */
    public TopCustomer(String firstName, String lastName, double totalPurchase) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.totalPurchase = totalPurchase;
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

    public double getTotalPurchase() {
        return totalPurchase;
    }

    public void setTotalPurchase(double totalPurchase) {
        this.totalPurchase = totalPurchase;
    }
}
