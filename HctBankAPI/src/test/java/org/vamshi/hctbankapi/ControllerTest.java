package org.vamshi.hctbankapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.vamshi.hctbankapi.controller.Controller;
import org.vamshi.hctbankapi.model.AccTransactions;
import org.vamshi.hctbankapi.model.CustAddress;
import org.vamshi.hctbankapi.model.CustDetails;
import org.vamshi.hctbankapi.responseModel.CustResponse;
import org.vamshi.hctbankapi.responseModel.GetBalance;
import org.vamshi.hctbankapi.responseModel.GetCustByIdResponse;
import org.vamshi.hctbankapi.responseModel.TransactionResponce;
import org.vamshi.hctbankapi.service.CustAccountService;
import org.vamshi.hctbankapi.service.GetBalanceService;
import org.vamshi.hctbankapi.service.TransactionService;
import org.vamshi.hctbankapi.supportClasses.GetRandom;
import org.vamshi.hctbankapi.supportClasses.supportTransaction;
import java.sql.Timestamp;
import java.util.*;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class ControllerTest {
    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();
    ObjectWriter objectWriter = objectMapper.writer();
    @Mock
    CustAccountService custAccountService;

    @Mock
    GetBalanceService getBalanceService;

    @Mock
    TransactionService transactionService;

    @InjectMocks
    Controller controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    GetRandom random = new GetRandom();


    CustResponse accoutn2 = new CustResponse("dummy", random.getRandom(), random.getRandom(), 00.0);
    CustResponse accoutn3 = new CustResponse("dummy", random.getRandom(), random.getRandom(), 00.0);

    @Test
    public void createAccount() throws Exception {
        CustDetails accountOpen_1 = new CustDetails(1L, "dummy", new CustAddress(random.getRandom(), "dummy", "dummy", "dummy", 00000L, new Timestamp(new Date().getTime())), 00000000000L, "dummy@gmail.com", new Timestamp(new Date().getTime()), new Timestamp(new Date().getTime()));
        CustResponse accountResponse1 = new CustResponse("dummy", random.getRandom(), random.getRandom(), 00.0);
        String string = objectWriter.writeValueAsString(accountOpen_1);
        Mockito.when(custAccountService.OpenCustomerAccount(accountOpen_1)).thenReturn(accountResponse1);
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/AccountOpening")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(string))
                .andExpect(status().isOk());
    }


    @Test
    public void getAccountDetails() throws Exception {
        Long Cust_ID = 377706815L;
        GetCustByIdResponse account = new GetCustByIdResponse("dummy", 0000L, "dummy@dummy.com", Cust_ID, random.getRandom(), new Timestamp(new Date().getTime()));
        Mockito.when(custAccountService.getCust_DetailsByCust_ID(Cust_ID)).thenReturn(ResponseEntity.ok(account));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/getAccount/{Cust_ID}",Cust_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    CustDetails record_1 = new CustDetails(random.getRandom(), "dummy", new CustAddress(random.getRandom(), "dummy", "dummy", "dummy", 0000L, new Timestamp(new Date().getTime())), 00000000000L, "dummy@gmail.com", new Timestamp(new Date().getTime()), new Timestamp(new Date().getTime()));
    CustDetails record_2 = new CustDetails(random.getRandom(), "dummy", new CustAddress(random.getRandom(), "dummy", "dummy", "dummy", 0000L, new Timestamp(new Date().getTime())), 00000000000L, "dummy@gmail.com", new Timestamp(new Date().getTime()), new Timestamp(new Date().getTime()));
    CustDetails record_3 = new CustDetails(random.getRandom(), "dummy", new CustAddress(random.getRandom(), "dummy", "dummy", "dummy", 0000L, new Timestamp(new Date().getTime())), 00000000000L, "dummy@gmail.com", new Timestamp(new Date().getTime()), new Timestamp(new Date().getTime()));

    @Test
    public void getAllCustomers() throws Exception {
        ResponseEntity<Object> list = new ResponseEntity<>(Arrays.asList(record_1, record_2, record_3), HttpStatusCode.valueOf(200));
        Mockito.when(custAccountService.getAllCustDetails()).thenReturn((ResponseEntity<Object>) list);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/getAllAccounts")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect((jsonPath("$[1].name", Matchers.is("dummy"))));
    }

    @Test
    public void getCustomerBalanceByCustID() throws Exception{
        GetBalance accountBalance = new GetBalance(17054225L, 500.00);
        Long custID = 377706815L;
        Mockito.when(getBalanceService.getCustBalanceByCustID(custID)).thenReturn(ResponseEntity.ok(accountBalance));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/getBalance")
                        .contentType(MediaType.APPLICATION_JSON)
                .param("custID",String.valueOf(custID)))
                .andExpect(status().isOk());
    }

    @Test
    public void getCustomerBalanceByAccID() throws Exception{
        GetBalance accountBalance = new GetBalance(17054225L, 500.00);
        Long accID = 377706815L;
        Mockito.when(getBalanceService.getCustBalanceByAccountID(accID)).thenReturn(ResponseEntity.ok(accountBalance));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/getBalance")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("accID",String.valueOf(accID)))
                .andExpect(status().isOk());
    }

    @Test
    public void customerTransactionRequest() throws Exception{
        Long accountID = 505129908L;
        Long toAccountID  = 478013539L;
        Long transactionReferenceID = 542006626L;
        supportTransaction transaction = new supportTransaction(accountID,toAccountID,100.0,"debit");
        TransactionResponce transactionResponce = new TransactionResponce("Transaction Successful", "HCT200", transactionReferenceID);
        String string = objectWriter.writeValueAsString(transaction);
        Mockito.when(transactionService.createTraction(transaction)).thenReturn(ResponseEntity.ok(transactionResponce));
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/transactionRequest")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(string))
                .andExpect(status().isOk());
    }


    AccTransactions transaction1 = new AccTransactions(123456789L, 123456789L, 12345L, 0.0, 0.0, 0.0,new Timestamp(new Date().getTime()));
    AccTransactions transaction2 = new AccTransactions(123456789L, 123456789L, 12345L, 0.0, 0.0, 0.0,new Timestamp(new Date().getTime()));
    AccTransactions transaction3 = new AccTransactions(123456789L, 12345678L, 12345L, 0.0, 0.0, 0.0,new Timestamp(new Date().getTime()));

    @Test
    public void getAllCustomerTransactions() throws Exception{
        List<AccTransactions> transactions = new ArrayList<>(Arrays.asList(transaction1,transaction2,transaction3));
        Mockito.when(transactionService.getAllTransactions()).thenReturn(transactions);
        mockMvc.perform(MockMvcRequestBuilders
                .get("/getAllTransactions")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    public void getAllCustomerTransactionsByRefID() throws Exception{
        Long transactionRefID = 123456789L;
        List<AccTransactions> transactions = new ArrayList<>(Arrays.asList(transaction1,transaction2));
        Mockito.when(transactionService.getTransactionByRefID(transactionRefID)).thenReturn(ResponseEntity.ok(transactions));
        mockMvc.perform(MockMvcRequestBuilders
                .get("/getTransactionsByReferenceId")
                .contentType(MediaType.APPLICATION_JSON)
                .param("refID", String.valueOf(transactionRefID)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void getAllCustomerTransactionsByRefIDAndTranID() throws Exception{
        Long transactionRefID = 123456789L;
        Long transactionID = 12345678L;
        AccTransactions transaction1 = new AccTransactions(12345678L, 123456789L, 12345L, 0.0, 0.0, 0.0,new Timestamp(new Date().getTime()));
        Mockito.when(transactionService.getTransactionByTransactionIDAndRefID(transactionRefID,transactionID)).thenReturn(ResponseEntity.ok(transaction1));
        mockMvc.perform(MockMvcRequestBuilders
                .get("/getTransactionByRefIDAndTranID")
                .param("refID", String.valueOf(transactionRefID))
                .param("tranID", String.valueOf(transactionID))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public  void getAllCustomerTransactionsByAccID() throws Exception{
        Long accID = 12345L;
        AccTransactions transaction1 = new AccTransactions(12345678L, 123456789L, 12345L, 0.0, 0.0, 0.0,new Timestamp(new Date().getTime()));
        Mockito.when(transactionService.getTransactionsByAccID(accID)).thenReturn(ResponseEntity.ok(transaction1));
        mockMvc.perform(MockMvcRequestBuilders
                .get("/getTransactionByAccountID")
                .param("accID", String.valueOf(accID))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }
}
