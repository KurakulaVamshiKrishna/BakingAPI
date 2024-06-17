package org.vamshi.hctbankapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.vamshi.hctbankapi.exception.ResponseException;
import org.vamshi.hctbankapi.model.AccBalance;
import org.vamshi.hctbankapi.model.AccTransactions;
import org.vamshi.hctbankapi.repositories.AccBalanceRepo;
import org.vamshi.hctbankapi.repositories.AccTransactionsRepo;
import org.vamshi.hctbankapi.repositories.CustAccMapRepo;
import org.vamshi.hctbankapi.repositories.CustDetailsRepo;
import org.vamshi.hctbankapi.responseModel.GetTransaction;
import org.vamshi.hctbankapi.responseModel.TransactionResponce;
import org.vamshi.hctbankapi.supportClasses.GetRandom;
import org.vamshi.hctbankapi.supportClasses.supportTransaction;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {


    @Autowired
    AccBalanceRepo accBalanceRepo;

    @Autowired
    AccTransactionsRepo accTransactionsRepo;

    @Autowired
    CustDetailsRepo custDetailsRepo;

    @Autowired
    CustAccMapRepo custAccMapRepo;
    Timestamp time = new Timestamp(new Date().getTime());
    GetRandom random = new GetRandom();

    public ResponseEntity createTraction(supportTransaction transaction) {
        Long debitTransactionId = random.getRandom();
        Long creditTractionID = random.getRandom();
        Long transactionReferenceID = random.getRandom();
        try {
            Long accID = transaction.getAccID();
            Long toAccID = transaction.getToAccID();
            Double amount = transaction.getAmount();
            String transactionType = transaction.getTypeOfTransaction();
            Optional<AccBalance> senderOptional = accBalanceRepo.findById(accID);
            Double fromAccBalance = senderOptional.get().getBalance();
            Double toAccBalance = accBalanceRepo.findById(toAccID).get().getBalance();
            if (fromAccBalance - amount < 0 || fromAccBalance < amount) {
                return ResponseEntity.status(HttpStatus.ACCEPTED).body("Insufficient funds...");
            }
            fromAccBalance -= amount;
            toAccBalance += amount;
            AccBalance sender = accBalanceRepo.findById(accID).get();
            AccBalance receiver = accBalanceRepo.findById(toAccID).get();
            sender.setBalance(fromAccBalance);
            receiver.setBalance(toAccBalance);
            accBalanceRepo.save(sender);
            accBalanceRepo.save(receiver);
            AccTransactions transactionDebit = new AccTransactions(debitTransactionId, transactionReferenceID, accID, 0.0, amount, fromAccBalance, time);
            AccTransactions transactionCredit = new AccTransactions(creditTractionID, transactionReferenceID, toAccID, amount, 0.0, toAccBalance, time);
            accTransactionsRepo.save(transactionCredit);
            accTransactionsRepo.save(transactionDebit);
            TransactionResponce transactionResponce = new TransactionResponce("Transaction Successful", "HCT200", transactionReferenceID);
            return ResponseEntity.ok(transactionResponce);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatusCode.valueOf(404)).body(new ResponseException("Provided Details are Invalid or all details are not provided!..", "HCTB400"));
        }
    }

    public List<AccTransactions> getAllTransactions() {
        List<AccTransactions> list = accTransactionsRepo.findAll();
        return list;
    }

    public ResponseEntity getTransactionByTransactionIDAndRefID(Long refID, Long transactionID) {
        try {
            if (refID == null || transactionID == null) {
                return ResponseEntity.status(HttpStatusCode.valueOf(404)).body(new ResponseException("Required Query parameter's are not provided!.", "HCTB400"));
            }
            Long acc_id = accTransactionsRepo.findById(transactionID).get().getAccountID();
            String name = custDetailsRepo.findById(custAccMapRepo.findByAccID(acc_id)).get().getName();
            Long transaction_ref_id = accTransactionsRepo.findById(transactionID).get().getTransactionRefID();
            if (!transaction_ref_id.equals(refID)) {
                return ResponseEntity.status(HttpStatusCode.valueOf(404)).body(new ResponseException("Provided input's {query params or path params} is/are Invalid!.", "HCTB404"));
            }
            double credit = accTransactionsRepo.findById(transactionID).get().getCredit();
            double debit = accTransactionsRepo.findById(transactionID).get().getDebit();
            double avil_balance = accTransactionsRepo.findById(transactionID).get().getAvlBalance();
            Timestamp transaction_date = accTransactionsRepo.findById(transactionID).get().getLastUpdate();
            return ResponseEntity.ok(new GetTransaction(name, acc_id, transaction_ref_id, credit, debit, avil_balance, transaction_date));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatusCode.valueOf(404)).body(new ResponseException("Provided input's {query params or path params} is/are Invalid!.", "HCTB404"));
        }
    }

    public ResponseEntity getTransactionByRefID(Long refID) {
        try {
            Optional<List<AccTransactions>> transaction = Optional.of(accTransactionsRepo.getTrasactios(refID));
            if (!transaction.get().isEmpty()) {
                System.out.println("hello");
                return ResponseEntity.ok(transaction);
            } else
                return ResponseEntity.status(HttpStatusCode.valueOf(404)).body(new ResponseException("Provided input's {query params or path params} is/are Invalid!.", "HCTB404"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatusCode.valueOf(404)).body(new ResponseException("Required Query parameter's are not provided!.", "HCTB400"));
        }
    }

    public ResponseEntity getTransactionsByAccID(Long accID) {
        try {
            if (accTransactionsRepo.getAccTransactionsByAccID(accID).isEmpty()) {
                return ResponseEntity.status(HttpStatusCode.valueOf(404)).body("No transactions or account found...!");
            } else {
                return ResponseEntity.ok(accTransactionsRepo.getAccTransactionsByAccID(accID));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatusCode.valueOf(404)).body(new ResponseException("Provided input's {query params or path params} is/are Invalid!.", "HCTB404"));
        }
    }
}
