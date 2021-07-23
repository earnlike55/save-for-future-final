package com.saving.saveforfuture.Service;

import com.saving.saveforfuture.Repository.BankLinkRepository;
import com.saving.saveforfuture.Repository.CustomerRepository;
import com.saving.saveforfuture.Repository.SavingRepository;
import com.saving.saveforfuture.model.*;
import com.saving.saveforfuture.service.CustomerService;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
import static org.hamcrest.MatcherAssert.assertThat;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)


public class CustomerServiceTest {
    @Autowired
    private CustomerService customerService;

    @MockBean
    private CustomerRepository customerRepository;

    @MockBean
    private SavingRepository savingRepository;

    @MockBean
    private BankLinkRepository bankLinkRepository;

    @Test
    public void getFinancialDetailSuccess(){
        List<CustomerProfileDetail> customerProfileDetailList = new ArrayList<>();
        CustomerProfileDetail customerProfileDetail = new CustomerProfileDetail()
                .setMonthlyExpense(new BigDecimal(2000))
                .setMonthlyIncome(new BigDecimal(30000))
                .setMemberno(2)
                .setBalance(new BigDecimal(50000))
                .setExpectAge(90)
                .setAgeOfRetirement(60);

        List<SavingDetail> savingDetailList = new ArrayList<>();
        SavingDetail savingDetail = new SavingDetail()
                .setSavingId(002)
                .setDateTime(Date.valueOf("1999-05-05"))
                .setDepositamt(new BigDecimal(3000));
        customerProfileDetailList.add(0,customerProfileDetail);
        savingDetailList.add(0,savingDetail);
        when(customerRepository.getCustomerFinancialDetail(anyLong())).thenReturn(customerProfileDetailList);
        when(savingRepository.getSavingDetail(anyLong())).thenReturn(savingDetailList);
        SavingResponse savingResponseTest;
        savingResponseTest = customerService.getCustomerFinancialDetail(001);
        assertEquals(0,savingResponseTest.getMonthlyExpense().compareTo(new BigDecimal(2000)));
        assertEquals(0,savingResponseTest.getMonthlyIncome().compareTo(new BigDecimal(30000)));
        assertEquals(0,savingResponseTest.getSuggestAmount().compareTo(new BigDecimal(720000)));
        assertEquals(0,savingResponseTest.getRemainingAmount().compareTo(new BigDecimal(670000)));

        assertEquals(0,savingResponseTest.getRemainingPercent().compareTo(new BigDecimal("93.00000")));
        assertEquals(0,savingResponseTest.getSavePercent().compareTo(new BigDecimal("6.90000")));
        assertThat(savingResponseTest.getSavingTransactions().get(0).getSavingId(),Matchers.equalTo(2L));
        assertEquals(0,savingResponseTest.getSavingTransactions().get(0).getCreatedDateTime().compareTo(Date.valueOf("1999-05-05")));
        assertEquals(0,savingResponseTest.getSavingTransactions().get(0).getDepositAmount().compareTo(new BigDecimal(3000)));

    }
    @Test
    public void getCustomerProfileSuccess(){
        List<CustomerProfileDetail> customerProfileDetailList = new ArrayList<>();
        CustomerProfileDetail customerProfileDetail = new CustomerProfileDetail()
                .setMonthlyExpense(new BigDecimal(2000))
                .setMonthlyIncome(new BigDecimal(30000))
                .setMemberno(2)
                .setBalance(new BigDecimal(50000))
                .setExpectAge(90)
                .setAgeOfRetirement(60);
        List<Profile> profileList = new ArrayList<>();
        Profile profile = new Profile()
                .setCustomerName("Sam")
                .setAge(30)
                .setBankAccNo("123-456")
                .setBankId(002)
                .setEmail("earn@hotmail.com")
                .setGender("male")
                .setBankName("KrungThai");
        customerProfileDetailList.add(0,customerProfileDetail);
        profileList.add(0,profile);
        when(customerRepository.getCustomerFinancialDetail(anyLong())).thenReturn(customerProfileDetailList);
        when(customerRepository.getCustomerProfile(anyLong())).thenReturn(profileList);
        ProfileResponse profileResponseTest = customerService.getCustomerProfile(001);
        assertThat(profileResponseTest.getCustomerName(),Matchers.equalTo("Sam"));
        assertThat(profileResponseTest.getAge(),Matchers.equalTo(30));
        assertThat(profileResponseTest.getBankAccNo(),Matchers.equalTo("123-456"));
        assertThat(profileResponseTest.getBankId(),Matchers.equalTo(002L));
        assertThat(profileResponseTest.getEmail(),Matchers.equalTo("earn@hotmail.com"));
        assertThat(profileResponseTest.getGender(),Matchers.equalTo("male"));
        assertThat(profileResponseTest.getBankName(),Matchers.equalTo("KrungThai"));
        assertEquals(0,profileResponseTest.getMonthlyExpense().compareTo(new BigDecimal(2000)));
        assertEquals(0,profileResponseTest.getMonthlyIncome().compareTo(new BigDecimal(30000)));
        assertEquals(0,profileResponseTest.getSuggestMonthly().compareTo(new BigDecimal(2000)));
    }

    @Test
    public void patchBankDetailSuccess(){
        List<CustomerProfileDetail> customerProfileDetailList = new ArrayList<>();
        CustomerProfileDetail customerProfileDetail = new CustomerProfileDetail()
                .setMonthlyExpense(new BigDecimal(2000))
                .setMonthlyIncome(new BigDecimal(30000))
                .setMemberno(2)
                .setBalance(new BigDecimal(50000))
                .setExpectAge(90)
                .setAgeOfRetirement(60);
        List<Profile> profileList = new ArrayList<>();
        Profile profile = new Profile()
                .setCustomerName("Sam")
                .setAge(30)
                .setBankId(002)
                .setEmail("earn@hotmail.com")
                .setGender("male")
                .setBankName("KrungThai");
        customerProfileDetailList.add(0,customerProfileDetail);
        profileList.add(0,profile);
        when(customerRepository.getCustomerFinancialDetail(anyLong())).thenReturn(customerProfileDetailList);
        when(customerRepository.getCustomerProfile(anyLong())).thenReturn(profileList);
        when(bankLinkRepository.getCustomerBankAccNo(any(),anyLong())).thenReturn("123-456-78");
        when(bankLinkRepository.getCustomerIdFromEmail(any())).thenReturn(001L);
        BankLinkResponse bankLinkResponseTest = customerService.patchBankDetail("earn@hotmail.com","123-456-78");
        ProfileResponse profileResponseTest = customerService.getCustomerProfile(001);
        assertThat(bankLinkResponseTest.getDescription(),Matchers.equalTo("Success"));
        assertThat(bankLinkResponseTest.getStatus(),Matchers.equalTo(1000));


    }

    @Test
    public void updateCustomerSuccess(){
        CustomerUpdateRequest customerUpdateRequest = new CustomerUpdateRequest()
                .setEmail("jason@hotmail.com")
                .setMemberNo(5)
                .setMonthlyExpense(new BigDecimal(6000))
                .setMonthlyIncome(new BigDecimal(100000));
        when(customerRepository.pathCustomerDetail(any(),any(),anyInt(),anyString(),anyLong())).thenReturn(1);
        CustomerUpdateResponse customerUpdateResponse = customerService.patchCustomerDetail(
                002,
                customerUpdateRequest.getEmail(),
                customerUpdateRequest.getMonthlyIncome(),
                customerUpdateRequest.getMonthlyExpense(),
                customerUpdateRequest.getMemberNo());
        assertThat(customerUpdateResponse.getDescription(),Matchers.equalTo("Success"));
        assertThat(customerUpdateResponse.getStatus(),Matchers.equalTo(1000));

    }

    @Test
    public void postCustomerSuccess(){
        CustomerInsertRequest request = new CustomerInsertRequest()
                .setCustomerName("Brandon")
                .setDob(Date.valueOf("1981-04-21"))
                .setGender("Male")
                .setEmail("brandon@gmail.com")
                .setPassword("Brandon1981")
                .setMonthlyIncome(new BigDecimal(40000))
                .setMonthlyExpense(new BigDecimal(3000))
                .setMemberNo(3)
                .setExpectAge(90)
                .setAgeOfRetirement(60);
        when(customerRepository.postCustomerDetail(any(),anyInt(),anyString())).thenReturn(1);
        CustomerInsertResponse response = customerService.postCustomerProfile(request);
        assertThat(response.getDescription(),Matchers.equalTo("Success"));
        assertThat(response.getStatus(),Matchers.equalTo(1000));

    }

    @Test
    public void postCustomerFail(){
        CustomerInsertRequest request = new CustomerInsertRequest()
                .setCustomerName("Brandon")
                .setDob(Date.valueOf("1981-04-21"))
                .setGender("Male")
                .setEmail("earn1@hotmail.com")
                .setPassword("Brandon1981")
                .setMonthlyIncome(new BigDecimal(40000))
                .setMonthlyExpense(new BigDecimal(3000))
                .setMemberNo(3)
                .setExpectAge(90)
                .setAgeOfRetirement(60);
        when(customerRepository.postCustomerDetail(any(),anyInt(),anyString())).thenReturn(-1);
        CustomerInsertResponse response = customerService.postCustomerProfile(request);
        assertThat(response.getDescription(),Matchers.equalTo("Email already exist"));
        assertThat(response.getStatus(),Matchers.equalTo(1999));
    }
}
