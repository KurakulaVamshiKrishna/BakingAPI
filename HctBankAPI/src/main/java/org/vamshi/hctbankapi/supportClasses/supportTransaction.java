package org.vamshi.hctbankapi.supportClasses;

import lombok.Data;

@Data
public class supportTransaction {
    Long accID;
    Long toAccID;
    Double amount;
    String typeOfTransaction;

    public supportTransaction() {
    }

    public supportTransaction(Long accID, Long toAccID, Double amount, String typeOfTransaction) {
        this.accID = accID;
        this.toAccID = toAccID;
        this.amount = amount;
        this.typeOfTransaction = typeOfTransaction;
    }

    public Long getAccID() {
        return accID;
    }

    public void setAccID(Long accID) {
        this.accID = accID;
    }

    public Long getToAccID() {
        return toAccID;
    }

    public void setToAccID(Long toAccID) {
        this.toAccID = toAccID;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getTypeOfTransaction() {
        return typeOfTransaction;
    }

    public void setTypeOfTransaction(String typeOfTransaction) {
        this.typeOfTransaction = typeOfTransaction;
    }
}
