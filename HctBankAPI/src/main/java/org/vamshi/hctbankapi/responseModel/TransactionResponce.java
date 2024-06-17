package org.vamshi.hctbankapi.responseModel;

import lombok.Data;

@Data
public class TransactionResponce {
    private String message;
    private String statusCode;
    private Long TransactionRefID;
    public TransactionResponce() {
    }
    public TransactionResponce(String message, String statusCode, Long transactionRefID) {
        this.message = message;
        this.statusCode = statusCode;
        TransactionRefID = transactionRefID;
    }
}
