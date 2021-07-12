package com.saving.saveforfuture.Repository;

import com.saving.saveforfuture.model.CustomerProfileDetail;
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
    public void getFinancialSuccess(){
        List<CustomerProfileDetail> check = customerRepository.getCustomerFinancialDetail("002");
        assertThat(check.size(), Matchers.equalTo(1));
        assertEquals(0, check.get(0).getMonthlyExpense().compareTo(new BigDecimal(3000)));
        assertEquals(0, check.get(0).getMonthlyIncome().compareTo(new BigDecimal(50000)));
        assertThat(check.get(0).getMemberno(), Matchers.equalTo(1));
        assertEquals(0, check.get(0).getTax().compareTo(new BigDecimal(1000)));
        assertEquals(0, check.get(0).getBalance().compareTo(new BigDecimal(20000)));
        assertThat(check.get(0).getExpectAge(),Matchers.equalTo(90));
        assertThat(check.get(0).getAgeOfRetirement(),Matchers.equalTo(60));
    }

    @Test
    public void test123()
    {
        BigDecimal test1 = new BigDecimal(60000);
        BigDecimal test2 = new BigDecimal(90000);
        test1.divide(test2, RoundingMode.FLOOR);
    }
}
