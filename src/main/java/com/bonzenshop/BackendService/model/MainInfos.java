package com.bonzenshop.BackendService.model;

import java.util.List;

/**
 * Klasse, welche alle Informationen beinhaltet, die für die Startseite des Webshops nötig sind.
 */
public class MainInfos {
    /**
     * Der Name des meistverkauften Produktes
     */
    String bestseller;
    /**
     * Liste der Top Kunden (mit dem höchsten Gesamteinkaufswert)
     */
    List<TopCustomer> topCustomerList;

    public String getBestseller() {
        return bestseller;
    }

    public void setBestseller(String bestseller) {
        this.bestseller = bestseller;
    }

    public List<TopCustomer> getTopCustomerList() {
        return topCustomerList;
    }

    public void setTopCustomerList(List<TopCustomer> topCustomerList) {
        this.topCustomerList = topCustomerList;
    }
}
