package com.bonzenshop.BackendService.model;

public class TopCustomer {
    int ranking;
    String firstName;
    String lastName;
    double totalPurchase;

    public TopCustomer(int ranking, String firstName, String lastName, double totalPurchase) {
        this.ranking = ranking;
        this.firstName = firstName;
        this.lastName = lastName;
        this.totalPurchase = totalPurchase;
    }

    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
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
