package com.saving.saveforfuture.Repository;


import org.checkerframework.checker.units.qual.A;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class LoginValidationRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Before
    public void before(){
        String sql = "INSERT INTO customer"+
                "(customerid,email,dob,age,memberno,monthlyincome,monthlyexpense,ageofretirement,\"password\",gender,savingid,expectage)"+
                "VALUES"+
                "(002,'aacc','1999-06-06',21,1,50000,3000,60,'123456','male',002,90)";
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

    public void get(){

    }
}
