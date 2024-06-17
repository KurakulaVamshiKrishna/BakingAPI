package org.vamshi.hctbankapi.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Table
@Data
public class CustDetails {

    @Id
    private Long Cust_ID;
    private String Name;
    @Setter
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "Address_ID")
    private CustAddress Address;

    private Long Phone;

    private String Email;

    private Timestamp Created;

    private Timestamp LastUpdated;

    public CustDetails() {
    }

    public CustDetails(Long cust_ID, String name, CustAddress address, Long phone, String email, Timestamp created, Timestamp lastUpdated) {
        Cust_ID = cust_ID;
        Name = name;
        Address = address;
        Phone = phone;
        Email = email;
        Created = created;
        LastUpdated = lastUpdated;
    }
}

