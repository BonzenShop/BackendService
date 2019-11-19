package com.bonzenshop.BackendService.model;

import java.util.List;

public class MainInfos {
    String bestseller;
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
