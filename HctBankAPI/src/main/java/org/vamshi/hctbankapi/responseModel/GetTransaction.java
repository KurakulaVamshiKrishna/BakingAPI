package org.vamshi.hctbankapi.responseModel;

import lombok.Data;
import java.sql.Timestamp;

@Data
public class GetTransaction {
    private String name;
    private Long acc_id;
    private Long transaction_ref_id;
    private double credit;
    private double debit;
    private double avil_balance;
    private Timestamp transaction_date;

    public GetTransaction() {
    }

    public GetTransaction(String name, Long acc_id, Long transaction_ref_id, double credit, double debit, double avil_balance, Timestamp transaction_date) {
        this.name = name;
        this.acc_id = acc_id;
        this.transaction_ref_id = transaction_ref_id;
        this.credit = credit;
        this.debit = debit;
        this.avil_balance = avil_balance;
        this.transaction_date = transaction_date;
    }
}
