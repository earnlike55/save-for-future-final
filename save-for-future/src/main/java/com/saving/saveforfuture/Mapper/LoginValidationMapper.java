package com.saving.saveforfuture.Mapper;


import com.saving.saveforfuture.model.LoginValidation;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginValidationMapper implements RowMapper<LoginValidation> {
    @Override
    public LoginValidation mapRow(ResultSet resultSet, int i) throws SQLException {
        return new LoginValidation()
                .setEmail(resultSet.getString("email"))
                .setPassword(resultSet.getString("password"))
                .setCustomerId(resultSet.getLong("customerid"))
                .setSalt(resultSet.getString("salt"));
    }
}
