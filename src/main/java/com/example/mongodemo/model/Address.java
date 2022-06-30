package com.example.mongodemo.model;

import lombok.Data;

@Data
public class Address {
    private String country;
    private String city;
    private String postcode;

    public Address(String country, String city, String postcode) {
        this.country = country;
        this.city = city;
        this.postcode = postcode;
    }
}
