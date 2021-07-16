package com.saving.saveforfuture.Repository;

import com.saving.saveforfuture.model.*;
import com.saving.saveforfuture.util.PasswordUtils;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
//import static org.junit.Assert.assertThat;
import static org.junit.Assert.*;
import static org.hamcrest.MatcherAssert.assertThat;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.util.List;
import java.util.StringJoiner;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class CustomerRepositoryTest {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Before
    public void before(){
        String sql = "INSERT INTO customer"+
                "(customerid,email,dob,age,memberno,monthlyincome,monthlyexpense,ageofretirement,\"password\",gender,savingid,expectage,customername,bankaccno)"+
                "VALUES"+
                "(2,'aa@hotmail.com','1999-06-06',21,1,50000,3000,60,'123456','male',2,90,'Sarah','123-456')";
        String sql2 = "INSERT INTO bank"+
                "(bankid,customerid,accounttype,balance,interest,bankname,bankaccno)"+
                "VALUES"+
                "(2,2,'deposit',20000,300,'KrungThai','123-456')";
        String sql3 = "INSERT INTO saving"+
                "(savingid,monthlysave,createdatetime,customerid)"+
                "VALUES"+
                "(2,3000,'1999-05-05 12:00:00.000','002')";
        jdbcTemplate.execute(sql);
        jdbcTemplate.execute(sql2);
        jdbcTemplate.execute(sql3);
    }

    @Test
    public void getFinancialSuccess(){
        List<CustomerProfileDetail> check = customerRepository.getCustomerFinancialDetail(002);
        assertThat(check.size(), Matchers.equalTo(1));
        assertEquals(0, check.get(0).getMonthlyExpense().compareTo(new BigDecimal(3000)));
        assertEquals(0, check.get(0).getMonthlyIncome().compareTo(new BigDecimal(50000)));
        assertThat(check.get(0).getMemberno(), Matchers.equalTo(1));
        assertEquals(0, check.get(0).getBalance().compareTo(new BigDecimal(20000)));
        assertThat(check.get(0).getExpectAge(),Matchers.equalTo(90));
        assertThat(check.get(0).getAgeOfRetirement(),Matchers.equalTo(60));
    }

    @Test
    public void getCustomerProfileSuccess(){
        List<Profile> profileList = customerRepository.getCustomerProfile(002);
        assertThat(profileList.size(),Matchers.equalTo(1));
        assertThat(profileList.get(0).getCustomerName(),Matchers.equalTo("Sarah"));
        assertThat(profileList.get(0).getAge(),Matchers.equalTo(21));
        assertThat(profileList.get(0).getBankId(),Matchers.equalTo(002L));
        assertThat(profileList.get(0).getBankName(),Matchers.equalTo("KrungThai"));
        assertThat(profileList.get(0).getGender(),Matchers.equalTo("male"));
        assertThat(profileList.get(0).getEmail(),Matchers.equalTo("aa@hotmail.com"));
        assertThat(profileList.get(0).getBankAccNo(),Matchers.equalTo("123-456"));
    }

    @Test
    public void updateCustomerSuccess()
    {
        long customerIdTest = 002;
        CustomerUpdateRequest customerUpdateRequest = new CustomerUpdateRequest()
                .setEmail("jason@hotmail.com")
                .setMemberNo(5)
                .setMonthlyExpense(new BigDecimal(6000))
                .setMonthlyIncome(new BigDecimal(100000));
        int effectRow = customerRepository.pathCustomerDetail(customerUpdateRequest.getMonthlyIncome(),
                customerUpdateRequest.getMonthlyExpense(),
                customerUpdateRequest.getMemberNo(),
                customerUpdateRequest.getEmail(),
                customerIdTest);
        assertThat(effectRow,Matchers.equalTo(1));
        List<Profile> profileList = customerRepository.getCustomerProfile(002);
        List<CustomerProfileDetail> customerProfileDetailList = customerRepository.getCustomerFinancialDetail(002);
        assertThat(profileList.get(0).getEmail(),Matchers.equalTo("jason@hotmail.com"));
        assertThat(customerProfileDetailList.get(0).getMemberno(),Matchers.equalTo(5));
        assertEquals(0,customerProfileDetailList.get(0).getMonthlyExpense().compareTo(new BigDecimal(6000)));
        assertEquals(0,customerProfileDetailList.get(0).getMonthlyIncome().compareTo(new BigDecimal(100000)));


    }

    @Test
    public void postCustomerDetailSuccess(){
        int age = 30;
        CustomerInsertRequest request = new CustomerInsertRequest()
                .setCustomerName("Brandon")
                .setDob(Date.valueOf("1981-04-21"))
                .setGender("Male")
                .setEmail("brand@gmail.com")
                .setPassword("Brandon1981")
                .setMonthlyIncome(new BigDecimal(40000))
                .setMonthlyExpense(new BigDecimal(3000))
                .setMemberNo(3)
                .setExpectAge(90)
                .setAgeOfRetirement(60);
        String salt = PasswordUtils.getSalt(30);
        int effectRow = customerRepository.postCustomerDetail(request,age,salt);
        assertThat(effectRow,Matchers.equalTo(1));

    }

    @Test
    public void postCustomerDetailFail(){
        int age = 30;
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
        String salt = PasswordUtils.getSalt(30);
        int effectRow = customerRepository.postCustomerDetail(request,age,salt);
        assertThat(effectRow,Matchers.equalTo(-1));

    }

    @Test
    public void test123()
    {
        BigDecimal test1 = new BigDecimal(60000);
        BigDecimal test2 = new BigDecimal(90000);
        test1.divide(test2, RoundingMode.FLOOR);
    }

}
