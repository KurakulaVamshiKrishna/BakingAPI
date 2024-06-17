package org.vamshi.hctbankapi.responseModel;

import lombok.Data;

@Data
public class CustResponse {
    private String Name;
    private Long Customer_ID;
    private Long Account_ID;
    private Double balance;
    public CustResponse(String name, Long customer_ID, Long account_ID, Double balance) {
        Name = name;
        Customer_ID = customer_ID;
        Account_ID = account_ID;
        this.balance = balance;
    }
}
