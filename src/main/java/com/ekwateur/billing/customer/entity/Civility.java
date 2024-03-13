package com.ekwateur.billing.customer.entity;

public enum Civility {
    MADAME("MME"), MADEMOISELLE("MLLE"), MONSIEUR("MR") ;
    private String title;

    Civility(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
