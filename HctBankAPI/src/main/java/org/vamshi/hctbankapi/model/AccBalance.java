package org.vamshi.hctbankapi.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Setter;

@Entity
@Table
@Data
public class AccBalance {
    @Id
    Long Acc_Id;
    Double Balance;

    public AccBalance() {
    }

    public AccBalance(Long acc_Id, Double balance) {
        Acc_Id = acc_Id;
        Balance = balance;
    }
}
