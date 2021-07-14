package com.saving.saveforfuture.Mapper;

import com.saving.saveforfuture.model.CustomerProfileDetail;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BankMapper implements RowMapper<CustomerProfileDetail> {
    @Override
    public CustomerProfileDetail mapRow(ResultSet resultSet, int i) throws SQLException {
        return new CustomerProfileDetail()
                .setEmail(resultSet.getString("email"));
    }

//    @Override
//    public String mapRows(ResultSet resultSet,int i)throws SQLException{
//        return resultSet.getString("bankaccno");
//    }
}
