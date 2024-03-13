package com.ekwateur.billing.energy;

public enum EnergyType {
    ELECTRICITY("Electricity"), GAZ("Gaz");
    private String title;


    EnergyType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
