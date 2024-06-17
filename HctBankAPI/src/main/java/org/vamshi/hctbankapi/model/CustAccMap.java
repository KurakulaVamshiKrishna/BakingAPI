package org.vamshi.hctbankapi.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Setter;

@Entity
@Table
@Data
public class CustAccMap {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;

    @OneToOne
    @JoinColumn(name = "acc_ID",referencedColumnName = "Acc_Id")
    AccBalance accBalance;

    @Setter
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cust_id", referencedColumnName = "Cust_ID")
    CustDetails Cust_ID;

    public CustAccMap() {
    }

    public CustAccMap(int id, AccBalance accBalance, CustDetails cust_ID) {
        this.id = id;
        this.accBalance = accBalance;
        Cust_ID = cust_ID;
    }
}
