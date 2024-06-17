package org.vamshi.hctbankapi.exception;

import lombok.Data;

@Data
public class ResponseException {
    private String message;
    private String reason_code;

    public ResponseException(String message, String reason_code) {
        this.message = message;
        this.reason_code = reason_code;
    }
}
