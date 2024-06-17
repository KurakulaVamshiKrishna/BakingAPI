package org.vamshi.hctbankapi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Time;
import java.sql.Timestamp;

@Entity
@Table
@Data
public class CustAddress {
    @Id
    private Long Address_ID;
    private String Country;
    private String City;
    private String AddressLane;
    private Long Pin;
    @UpdateTimestamp
    private Timestamp LastUpdate;

    public CustAddress() {
    }

    public CustAddress(Long address_ID, String country, String city, String addressLane, Long pin, Timestamp lastUpdate) {
        Address_ID = address_ID;
        Country = country;
        City = city;
        AddressLane = addressLane;
        Pin = pin;
        LastUpdate = lastUpdate;
    }
}
