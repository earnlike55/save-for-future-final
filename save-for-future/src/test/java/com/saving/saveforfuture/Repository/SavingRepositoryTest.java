package com.saving.saveforfuture.Repository;


import com.saving.saveforfuture.model.SavingDetail;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
import static org.hamcrest.MatcherAssert.assertThat;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class SavingRepositoryTest {
    @Autowired
    private SavingRepository savingRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Before
    public void before(){
        String sql = "INSERT INTO customer"+
                "(customerid,email,dob,age,memberno,monthlyincome,monthlyexpense,tax,ageofretirement,\"password\",gender,savingid,expectage)"+
                "VALUES"+
                "('002','aacc','1999-06-06',21,1,50000,3000,1000,60,'123456','male','A2',90)";
        String sql2 = "INSERT INTO bank"+
                "(bankid,customerid,accounttype,balance,interest)"+
                "VALUES"+
                "('A2','002','deposit',20000,300)";
        String sql3 = "INSERT INTO saving"+
                "(savingid,monthlysave,createdatetime,customerid)"+
                "VALUES"+
                "('A2',3000,'1999-05-05 12:00:00.000','002')";
        jdbcTemplate.execute(sql);
        jdbcTemplate.execute(sql2);
        jdbcTemplate.execute(sql3);
    }


    @Test
    public void getSavingDetailSuccess()
    {
        List<SavingDetail> savingTest = savingRepository.getSavingDetail("002");
        assertThat(savingTest.size(), Matchers.equalTo(1));
        assertEquals(0,savingTest.get(0).getDepositamt().compareTo(new BigDecimal(3000)));
        assertEquals(0,savingTest.get(0).getDateTime().compareTo(Date.valueOf("1999-05-05")));
        assertThat(savingTest.get(0).getSavingId(),Matchers.equalTo("A2"));
    }

}
