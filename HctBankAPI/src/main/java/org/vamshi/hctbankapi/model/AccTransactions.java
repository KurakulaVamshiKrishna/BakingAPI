package org.vamshi.hctbankapi.model;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Table
@Data
public class AccTransactions {
    @Id
    Long TransactionID;
    Long TransactionRefID;
    Long AccountID;
    Double Credit;
    Double Debit;
    Double AvlBalance;
    Timestamp LastUpdate;

    public AccTransactions() {
    }

    public AccTransactions(Long transactionID, Long transactionRefID, Long accountID, Double credit, Double debit, Double avlBalance, Timestamp lastUpdate) {
        TransactionID = transactionID;
        TransactionRefID = transactionRefID;
        AccountID = accountID;
        Credit = credit;
        Debit = debit;
        AvlBalance = avlBalance;
        LastUpdate = lastUpdate;
    }
}
