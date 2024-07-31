package com.mdrsolutions.SpringJmsExample01.pojos;

public class Customer {

    private final String customerId;
    private final String fullName;

    public Customer(String customerId, String fullName) {
        this.customerId = customerId;
        this.fullName = fullName;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getFullName() {
        return fullName;
    }
}
