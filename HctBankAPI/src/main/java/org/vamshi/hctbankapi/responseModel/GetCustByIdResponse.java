package org.vamshi.hctbankapi.responseModel;

import lombok.Data;
import java.sql.Timestamp;

@Data
public class GetCustByIdResponse {
    private String Name;
    private Long Phone_number;
    private String email;
    private Long customer_ID;
    private Long account_ID;
    private Timestamp created_on;

    public GetCustByIdResponse() {
    }

    public GetCustByIdResponse(String name, Long phone_number, String email, Long customer_ID, Long account_ID, Timestamp created_on) {
        Name = name;
        Phone_number = phone_number;
        this.email = email;
        this.customer_ID = customer_ID;
        this.account_ID = account_ID;
        this.created_on = created_on;
    }
}
