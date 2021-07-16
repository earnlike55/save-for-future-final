package com.saving.saveforfuture.Mapper;


import com.saving.saveforfuture.model.CustomerProfileDetail;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.Assert.*;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest

public class CustomerMapperTest {

    private CustomerMapper customerMapper = new CustomerMapper();

    @Test
    public void customerMappingTest()throws SQLException {
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getInt("memberno")).thenReturn(1);
        when(resultSet.getBigDecimal("monthlyincome")).thenReturn(new BigDecimal(50000));
        when(resultSet.getBigDecimal("monthlyexpense")).thenReturn(new BigDecimal(3000));
        when(resultSet.getInt("expectage")).thenReturn(90);
        when(resultSet.getBigDecimal("balance")).thenReturn(new BigDecimal(20000));
        when(resultSet.getInt("ageofretirement")).thenReturn(60);

        CustomerProfileDetail customerProfileDetail = customerMapper.mapRow(resultSet,0);

        assertNotNull(customerProfileDetail);
        assertThat(customerProfileDetail.getMemberno(), Matchers.equalTo(1));
        assertEquals(0,customerProfileDetail.getMonthlyIncome().compareTo(new BigDecimal(50000)));
        assertEquals(0,customerProfileDetail.getMonthlyExpense().compareTo(new BigDecimal(3000)));
        assertThat(customerProfileDetail.getExpectAge(),Matchers.equalTo(90));
        assertEquals(0,customerProfileDetail.getBalance().compareTo(new BigDecimal(20000)));
        assertThat(customerProfileDetail.getAgeOfRetirement(),Matchers.equalTo(60));
    }
}
