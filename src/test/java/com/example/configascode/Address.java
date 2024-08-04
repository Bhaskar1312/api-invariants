package com.example.configascode;

public class Address {
    private String streetNo;
    private int[] pincodes; // try later for indices without getter


    public Address() {

    }

    public String getStreetNo() {
        return streetNo;
    }

    public void setStreetNo(String streetNo) {
        this.streetNo = streetNo;
    }

    public int[] getPincodes() {
        return pincodes;
    }

    public void setPincodes(int[] pincodes) {
        this.pincodes = pincodes;
    }
}
