package com.bonzenshop.BackendService.model;

import java.util.List;

public class MainInfos {
    int bestsellerId;
    List<TopCustomer> topCustomerList;

    public int getBestsellerId() {
        return bestsellerId;
    }

    public void setBestsellerId(int bestsellerId) {
        this.bestsellerId = bestsellerId;
    }

    public List<TopCustomer> getTopCustomerList() {
        return topCustomerList;
    }

    public void setTopCustomerList(List<TopCustomer> topCustomerList) {
        this.topCustomerList = topCustomerList;
    }
}
