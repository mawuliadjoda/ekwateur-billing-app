package com.ekwateur.billing.customer.entity;

public enum CustomerType {
    PRO("Professional"), PAR("Particular");
    private String title;

    CustomerType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
