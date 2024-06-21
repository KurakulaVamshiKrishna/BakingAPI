package org.vamshi.hctbankapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.vamshi.hctbankapi.exception.ResponseException;
import org.vamshi.hctbankapi.model.AccTransactions;
import org.vamshi.hctbankapi.model.CustDetails;
import org.vamshi.hctbankapi.repositories.CustAccMapRepo;
import org.vamshi.hctbankapi.responseModel.CustResponse;
import org.vamshi.hctbankapi.service.CustAccountService;
import org.vamshi.hctbankapi.service.GetBalanceService;
import org.vamshi.hctbankapi.service.TransactionService;
import org.vamshi.hctbankapi.supportClasses.supportTransaction;


import java.util.List;
import java.util.Objects;

@RestController
public class Controller {

    @Autowired
    CustAccountService custAccountService;

    @Autowired
    GetBalanceService getBalanceService;

    @Autowired
    TransactionService transactionService;

    @Autowired
    CustAccMapRepo custAccMapRepo;

    @PostMapping("/AccountOpening")
    public CustResponse openAccount(@RequestBody CustDetails account) {
        return custAccountService.OpenCustomerAccount(account);
    }

    @GetMapping("/getAccount/{Cust_ID}")
    public ResponseEntity getAccount(@PathVariable Long Cust_ID) {
        return custAccountService.getCust_DetailsByCust_ID(Cust_ID);
    }

    @GetMapping("/getAllAccounts")
    public ResponseEntity<Object> getAllAccounts() {
        return custAccountService.getAllCustDetails();
    }

    @GetMapping("/getBalance")
    public ResponseEntity getBalance(@RequestParam(required = false) Long custID, @RequestParam(required = false) Long accID) {
        if (accID == null && custID == null) {
            return ResponseEntity.status(HttpStatusCode.valueOf(400)).body(new ResponseException("Required Query parameter's are not provided...", "HCTB400"));
        } else if (custID == null && accID != null) {
            return getBalanceService.getCustBalanceByAccountID(accID);
        } else if (custID != null && accID == null) {
            return getBalanceService.getCustBalanceByCustID(custID);
        } else {
            Long custID2 = custAccMapRepo.findByAccID(accID);
            if(!Objects.equals(custID2, custID)){
                return ResponseEntity.status(HttpStatusCode.valueOf(404)).body(new ResponseException("Provided input's {query params or path params} is/are Invalid!.", "HCTB404"));
            }else{
                return getBalanceService.getCustBalanceByAccountID(accID);
            }
        }
    }

    @PostMapping("/transactionRequest")
    public ResponseEntity transactionRequest(@RequestBody supportTransaction transaction){
        return transactionService.createTraction(transaction);
    }

    @GetMapping("/getAllTransactions")
    public List<AccTransactions> getAllTransactions(){
        return transactionService.getAllTransactions();
    }

    @GetMapping("/getTransactionsByReferenceId")
    public ResponseEntity getTransactionByRefID(@RequestParam(required = false) Long refID){
        if(refID == null){
            return ResponseEntity.status(HttpStatusCode.valueOf(400)).body(new ResponseException("Provided input's {query params or path params} is/are not provided!.", "HCTB400"));
        }
        return transactionService.getTransactionByRefID(refID);
    }

    @GetMapping("/getTransactionByRefIDAndTranID")
    public ResponseEntity getTransactionByRefAndTan(@RequestParam(required = false) Long refID, @RequestParam(required = false) Long tranID){
        return transactionService.getTransactionByTransactionIDAndRefID(refID, tranID);
    }

    @GetMapping("/getTransactionByAccountID")
    public  ResponseEntity getTransactionsByAccountID(@RequestParam(required = false) Long accID){
        if(accID == null){
            return ResponseEntity.status(HttpStatusCode.valueOf(404)).body(new ResponseException("Required Query parameter's are not provided!.", "HCTB400"));
        }else{
            return transactionService.getTransactionsByAccID(accID);
        }
    }
}
