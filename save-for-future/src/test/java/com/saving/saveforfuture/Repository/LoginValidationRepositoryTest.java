package com.saving.saveforfuture.Repository;


import com.saving.saveforfuture.model.LoginValidation;
import org.checkerframework.checker.units.qual.A;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;
import static org.hamcrest.MatcherAssert.assertThat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class LoginValidationRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private LoginValidationRepository loginValidationRepository;

    @Before
    public void before(){
        String sql = "INSERT INTO customer"+
                "(customerid,email,dob,age,memberno,monthlyincome,monthlyexpense,ageofretirement,\"password\",gender,savingid,expectage,salt)"+
                "VALUES"+
                "(002,'aa@hotmail.com','1999-06-06',21,1,50000,3000,60,'123456','male',002,90,'12234')";
        String sql2 = "INSERT INTO bank"+
                "(bankid,customerid,accounttype,balance,interest)"+
                "VALUES"+
                "(002,002,'deposit',20000,300)";
        String sql3 = "INSERT INTO saving"+
                "(savingid,monthlysave,createdatetime,customerid)"+
                "VALUES"+
                "(002,3000,'1999-05-05 12:00:00.000',002)";
        jdbcTemplate.execute(sql);
        jdbcTemplate.execute(sql2);
        jdbcTemplate.execute(sql3);
    }

    @Test
    public void getEmailSuccess(){
        LoginValidation loginValidationList = loginValidationRepository.selectEmailPassword("aa@hotmail.com","123456");
        assertNotNull(loginValidationList);
        assertThat(loginValidationList.getEmail(),Matchers.equalTo("aa@hotmail.com"));
        assertThat(loginValidationList.getPassword(),Matchers.equalTo("123456"));
        assertThat(loginValidationList.getSalt(),Matchers.equalTo("12234"));
        assertThat(loginValidationList.getCustomerId(),Matchers.equalTo(002L));

    }

    @Test
    public void getEmailFail(){
        LoginValidation loginValidationList = new LoginValidation();
        try{
            loginValidationList = loginValidationRepository.selectEmailPassword("test","123456");
        }catch (EmptyResultDataAccessException e){
//
            assertThat(loginValidationList.getEmail(),Matchers.equalTo(null));
            System.err.println(e);
        }
    }

    @Test
    public void getSaltSucess(){
        String salts = loginValidationRepository.getSalt("aa@hotmail.com");
        assertThat(salts,Matchers.equalTo("12234"));
    }
}
