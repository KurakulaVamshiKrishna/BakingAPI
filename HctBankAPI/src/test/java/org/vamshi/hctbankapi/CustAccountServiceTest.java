package org.vamshi.hctbankapi;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.vamshi.hctbankapi.model.AccBalance;
import org.vamshi.hctbankapi.model.CustAccMap;
import org.vamshi.hctbankapi.model.CustAddress;
import org.vamshi.hctbankapi.model.CustDetails;
import org.vamshi.hctbankapi.repositories.*;
import org.vamshi.hctbankapi.responseModel.CustResponse;
import org.vamshi.hctbankapi.responseModel.GetCustByIdResponse;
import org.vamshi.hctbankapi.service.CustAccountService;
import org.vamshi.hctbankapi.supportClasses.GetRandom;
import java.sql.Timestamp;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class CustAccountServiceTest {
    @Mock
    AccBalanceRepo accBalanceRepo;
    @Mock
    CustAccMapRepo custAccMapRepo;
    @Mock
    CustAddressRepo custAddressRepo;
    @Mock
    CustDetailsRepo custDetailsRepo;
    @Mock
    GetRandom random;
    @InjectMocks
    CustAccountService custAccountService;

    CustAddress custAddress1 = new CustAddress(123456789L, "dummy", "dummy", "dummy", 00000L, new Timestamp(new Date().getTime()));
    CustAddress custAddress2 = new CustAddress(123456789L, "dummy", "dummy", "dummy", 00000L, new Timestamp(new Date().getTime()));
    CustDetails custDetails1 = new CustDetails(1234567890L, "dummy", new CustAddress(123456789L, "dummy", "dummy", "dummy", 00000L, new Timestamp(new Date().getTime())), 000000000L, "dummy@dummy.com", new Timestamp(new Date().getTime()), new Timestamp(new Date().getTime()));
    CustDetails custDetails2 = new CustDetails(1234567890L, "dummy", new CustAddress(123456789L, "dummy", "dummy", "dummy", 00000L, new Timestamp(new Date().getTime())), 000000000L, "dummy@dummy.com", new Timestamp(new Date().getTime()), new Timestamp(new Date().getTime()));
    AccBalance accBalance1 = new AccBalance(12345L, 500.0);
    AccBalance accBalance2 = new AccBalance(12345L, 500.0);
    CustAccMap custAccMap1 = new CustAccMap(1, new AccBalance(12345L, 500.0), new CustDetails(1234567890L, "dummy", new CustAddress(123456789L, "dummy", "dummy", "dummy", 00000L, new Timestamp(new Date().getTime())), 000000000L, "dummy@dummy.com", new Timestamp(new Date().getTime()), new Timestamp(new Date().getTime())));
    CustAccMap custAccMap2 = new CustAccMap(1, new AccBalance(12345L, 500.0), new CustDetails(1234567890L, "dummy", new CustAddress(123456789L, "dummy", "dummy", "dummy", 00000L, new Timestamp(new Date().getTime())), 000000000L, "dummy@dummy.com", new Timestamp(new Date().getTime()), new Timestamp(new Date().getTime())));
    CustResponse custResponse1 = new CustResponse("dummy", 1234567890L, 12345L, 500.0);
    GetCustByIdResponse getCustByIdResponse = new GetCustByIdResponse("Dummy", 123456789L, "dummy.dummy.com", 1234567890L, 12345L, new Timestamp(new Date().getTime()));

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void openCustomerAccount() {
        Long randomValue = random.getRandom();
        Timestamp time = new Timestamp(new Date().getTime());

        when(random.getRandom()).thenReturn(randomValue);

        CustDetails custDetails3 = new CustDetails();
        custDetails3.setCust_ID(12345L);
        custDetails3.setName("DummyName");
        custDetails3.setAddress(custAddress1);
        custDetails3.setPhone(123456789L);
        custDetails3.setEmail("dummy@dummmy.com");
        custDetails3.setCreated(time);
        custDetails3.setLastUpdated(time);

        assertEquals(12345L, custDetails3.getCust_ID());
        assertEquals("DummyName", custDetails3.getName());
        assertEquals(custAddress1, custDetails3.getAddress());
        assertEquals(123456789L, custDetails3.getPhone());
        assertEquals("dummy@dummmy.com", custDetails3.getEmail());
        assertEquals(time, custDetails3.getCreated());
        assertEquals(time, custDetails3.getLastUpdated());

        ArgumentCaptor<CustDetails> custDetailsArgumentCaptor = ArgumentCaptor.forClass(CustDetails.class);
        ArgumentCaptor<CustAddress> custAddressArgumentCaptor = ArgumentCaptor.forClass(CustAddress.class);
        ArgumentCaptor<AccBalance> accBalanceArgumentCaptor = ArgumentCaptor.forClass(AccBalance.class);
        ArgumentCaptor<CustAccMap> custAccMapArgumentCaptor = ArgumentCaptor.forClass(CustAccMap.class);

        when(custDetailsRepo.save(custDetailsArgumentCaptor.capture())).thenReturn(custDetails1);
        when(accBalanceRepo.save(accBalanceArgumentCaptor.capture())).thenReturn(accBalance1);
        when(custAccMapRepo.save(custAccMapArgumentCaptor.capture())).thenReturn(custAccMap1);

        when(custDetailsRepo.save(Mockito.any(CustDetails.class))).thenReturn(custDetails3);
        when(accBalanceRepo.save(Mockito.any(AccBalance.class))).thenReturn(new AccBalance(12345L, 500.0));
        when(custAccMapRepo.save(Mockito.any(CustAccMap.class))).thenReturn(new CustAccMap(1, new AccBalance(12345L, 500.0), custDetails3));

        CustDetails custDetails = new CustDetails(0000L, "dummy", custAddress1, 00000000000L, "dummy@gmail.com", new Timestamp(new Date().getTime()), new Timestamp(new Date().getTime()));
        CustResponse actualAccountOpeningResponse = new CustResponse("dummy", 0000L, 0000L, 500.0);
        CustResponse obtainedAccountOpeningResponse = custAccountService.OpenCustomerAccount(custDetails);
        assertEquals(actualAccountOpeningResponse, obtainedAccountOpeningResponse);
    }
}
