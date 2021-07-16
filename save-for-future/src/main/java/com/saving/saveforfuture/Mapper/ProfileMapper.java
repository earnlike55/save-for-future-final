package com.saving.saveforfuture.Mapper;


import com.saving.saveforfuture.model.Profile;
import com.saving.saveforfuture.model.ProfileResponse;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProfileMapper implements RowMapper<Profile> {
    @Override
    public Profile mapRow(ResultSet resultSet, int i) throws SQLException {
        return new Profile()
                .setCustomerName(resultSet.getString("customername"))
                .setAge(resultSet.getInt("age"))
                .setBankName(resultSet.getString("bankname"))
                .setGender(resultSet.getString("gender"))
                .setEmail(resultSet.getString("email"))
                .setBankAccNo(resultSet.getString("bankaccno"))
                .setBankId(resultSet.getLong("bankid"));
    }
}
