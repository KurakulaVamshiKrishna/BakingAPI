package org.vamshi.hctbankapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.vamshi.hctbankapi.exception.ResponseException;
import org.vamshi.hctbankapi.model.AccBalance;
import org.vamshi.hctbankapi.model.CustAccMap;
import org.vamshi.hctbankapi.model.CustDetails;
import org.vamshi.hctbankapi.repositories.AccBalanceRepo;
import org.vamshi.hctbankapi.repositories.CustAccMapRepo;
import org.vamshi.hctbankapi.repositories.CustAddressRepo;
import org.vamshi.hctbankapi.repositories.CustDetailsRepo;
import org.vamshi.hctbankapi.responseModel.CustResponse;
import org.vamshi.hctbankapi.responseModel.GetCustByIdResponse;
import org.vamshi.hctbankapi.supportClasses.GetRandom;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CustAccountService {
    @Autowired
    CustDetailsRepo custDetailsRepo;

    @Autowired
    CustAddressRepo custAddressRepo;

    @Autowired
    CustAccMapRepo custAccMapRepo;

    @Autowired
    AccBalanceRepo accBalanceRepo;

    Timestamp time = new Timestamp(new Date().getTime());

    GetRandom random = new GetRandom();

    public CustResponse OpenCustomerAccount(CustDetails custDetails) {
        //support instances and variables
        Long accountId = random.getRandom();
        Long custID = random.getRandom();
        Long addressID = random.getRandom();
        Double minBalance = 500.00;
        AccBalance accBalance = new AccBalance();
        CustAccMap custAccMap = new CustAccMap();

        //creating customer account
        custDetails.setCust_ID(custID);
        custDetails.getAddress().setAddress_ID(addressID);
        custDetails.setCreated(time);
        custDetails.setLastUpdated(time);
        CustDetails c = custDetailsRepo.save(custDetails);

        //adding balance
        accBalance.setAcc_Id(accountId);
        accBalance.setBalance(minBalance);
        AccBalance ab = accBalanceRepo.save(accBalance);

        //custAccount map
        custAccMap.setCust_ID(c);
        custAccMap.setAccBalance(ab);
        custAccMapRepo.save(custAccMap);

        return new CustResponse(custDetails.getName(), custDetails.getCust_ID(), accountId, minBalance);
    }

    public ResponseEntity getCust_DetailsByCust_ID(Long custId) {
        try {
            Optional<CustDetails> acc = Optional.of(custDetailsRepo.findById(custId).get());
            return ResponseEntity.ok(new GetCustByIdResponse(acc.get().getName(), acc.get().getPhone(), acc.get().getEmail(), acc.get().getCust_ID(), custAccMapRepo.findByCustID(acc.get().getCust_ID()), acc.get().getCreated()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatusCode.valueOf(404)).body(new ResponseException("Provided input's {query params or path params} is/are Invalid!.", "hct404"));
        }
    }

    public ResponseEntity<Object> getAllCustDetails() {
        try{
            List<CustDetails> allAccounts = custDetailsRepo.findAll();
            return ResponseEntity.ok(allAccounts);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatusCode.valueOf(404)).body(new ResponseException("No Cust Details are Found...", "hct404"));
        }
    }
}
