package com.saving.saveforfuture.Mapper;

import com.saving.saveforfuture.model.BankDetail;
import com.saving.saveforfuture.model.CustomerProfileDetail;
import org.springframework.jdbc.core.RowMapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class CustomerMapper implements RowMapper<CustomerProfileDetail> {

    @Override
    public CustomerProfileDetail mapRow(ResultSet resultSet, int i) throws SQLException {
        return new CustomerProfileDetail()
                .setMemberno(resultSet.getInt("memberno"))
                .setMonthlyIncome(resultSet.getBigDecimal("monthlyincome"))
                .setMonthlyExpense(resultSet.getBigDecimal("monthlyexpense"))
                .setExpectAge(resultSet.getInt("expectage"))
                .setBalance(resultSet.getBigDecimal("balance"))
                .setAgeOfRetirement(resultSet.getInt("ageofretirement"));
    }



//    public  ZonedDateTime DateTimeToZoneDateTime(Date date){
//        ZoneId defaultZoneId = ZoneId.systemDefault();
//        // Convert Date to Instant.
//        Instant instant = date.toInstant();
//        // Instant + default time zone.
//        ZonedDateTime zonedDateTime = instant.atZone(defaultZoneId);
//        return zonedDateTime;
//    }
}
