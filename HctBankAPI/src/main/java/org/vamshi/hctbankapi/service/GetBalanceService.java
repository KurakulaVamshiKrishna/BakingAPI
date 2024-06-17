package org.vamshi.hctbankapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.vamshi.hctbankapi.exception.ResponseException;
import org.vamshi.hctbankapi.model.AccBalance;
import org.vamshi.hctbankapi.repositories.AccBalanceRepo;
import org.vamshi.hctbankapi.repositories.CustAccMapRepo;
import org.vamshi.hctbankapi.repositories.CustAddressRepo;
import org.vamshi.hctbankapi.repositories.CustDetailsRepo;
import org.vamshi.hctbankapi.responseModel.GetBalance;

import java.util.Optional;

@Service
public class GetBalanceService {
    @Autowired
    CustDetailsRepo custDetailsRepo;

    @Autowired
    CustAddressRepo custAddressRepo;

    @Autowired
    CustAccMapRepo custAccMapRepo;

    @Autowired
    AccBalanceRepo accBalanceRepo;

    public ResponseEntity getCustBalanceByAccountID(Long accID) {
        try {
            Optional<AccBalance> accountBalance = accBalanceRepo.findById(accID);
            if (!accountBalance.isEmpty())
                return ResponseEntity.ok(new GetBalance(accountBalance.get().getAcc_Id(), accountBalance.get().getBalance()));
            else {
                return ResponseEntity.status(HttpStatusCode.valueOf(404)).body(new ResponseException("Provided input's {query params or path params} is/are Invalid!.", "HCTB404"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatusCode.valueOf(404)).body(new ResponseException("Provided input's {query params or path params} is/are Invalid!.", "HCTB404"));
        }
    }

    public ResponseEntity getCustBalanceByCustID(Long custID) {
        try {
            Long accountID = custAccMapRepo.findByCustID(custID);
            ResponseEntity accountBalance =  getCustBalanceByAccountID(accountID);
            System.out.println(accountBalance);
            return ResponseEntity.ok(accountBalance.getBody());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatusCode.valueOf(404)).body(new ResponseException("Provided input's {query params or path params} is/are Invalid!.", "HCTB404"));
        }
    }
}
