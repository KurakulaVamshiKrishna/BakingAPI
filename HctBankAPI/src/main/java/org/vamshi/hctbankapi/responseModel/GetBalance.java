package org.vamshi.hctbankapi.responseModel;

import lombok.Data;

@Data
public class GetBalance {
    private Long account_ID;
    private Double aval_balance;

    public GetBalance(Long account_ID, Double aval_balance) {
        this.account_ID = account_ID;
        this.aval_balance = aval_balance;
    }

    public Long getAccount_ID() {
        return account_ID;
    }

    public void setAccount_ID(Long account_ID) {
        this.account_ID = account_ID;
    }

    public Double getAval_balance() {
        return aval_balance;
    }

    public void setAval_balance(Double aval_balance) {
        this.aval_balance = aval_balance;
    }
}
