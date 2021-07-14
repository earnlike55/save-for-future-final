package com.saving.saveforfuture.Repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
import static org.hamcrest.MatcherAssert.assertThat;
import org.hamcrest.Matchers;

import javax.transaction.Transactional;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class BankLinkRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private BankLinkRepository bankLinkRepository;

    @Before
    public void before(){
        String sql = "INSERT INTO customer"+
                "(customerid,email,dob,age,memberno,monthlyincome,monthlyexpense,tax,ageofretirement,\"password\",gender,savingid,expectage,customername)"+
                "VALUES"+
                "('002','aa@hotmail.com','1999-06-06',21,1,50000,3000,1000,60,'123456','male','A2',90,'Sarah')";
        String sql2 = "INSERT INTO bank"+
                "(bankid,customerid,accounttype,balance,interest,bankname,bankaccno)"+
                "VALUES"+
                "('B2','002','deposit',20000,300,'KrungThai','123-45678')";
        String sql3 = "INSERT INTO saving"+
                "(savingid,monthlysave,createdatetime,customerid)"+
                "VALUES"+
                "('A2',3000,'1999-05-05 12:00:00.000','002')";
        jdbcTemplate.execute(sql);
        jdbcTemplate.execute(sql2);
        jdbcTemplate.execute(sql3);
    }

    @Test
    public void getIdByEmailSuccess(){
        String cusId = bankLinkRepository.getCustomerIdFromEmail("aa@hotmail.com");
        assertThat(cusId,Matchers.equalTo("002"));
    }

    @Test
    public void getCustomerBankAcc(){
        String accNo = bankLinkRepository.getCustomerBankAccNo("123-45678");
        assertThat(accNo,Matchers.equalTo("123-45678"));
    }

    @Test
    public void getIdException(){
        String cusId = "";
        try {
            cusId = bankLinkRepository.getCustomerIdFromEmail("aabvc");
        }
        catch (EmptyResultDataAccessException e){

        }
        assertThat(cusId,Matchers.equalTo(null));

    }

    @Test
    public void getCustomerAccException(){
        String cusId = "";
        try {
            cusId = bankLinkRepository.getCustomerBankAccNo("1");
        }
        catch (EmptyResultDataAccessException e){

        }
        assertThat(cusId,Matchers.equalTo(null));

    }

    @Test
    public void updateBankSuccess(){
        int check = bankLinkRepository.updateBankDetail("002","123-45678");
        assertThat(check,Matchers.equalTo(1));
    }
}
