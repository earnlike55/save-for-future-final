package com.saving.saveforfuture.Repository;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.saving.saveforfuture.Mapper.CustomerMapper;
import com.saving.saveforfuture.Mapper.ProfileMapper;
import com.saving.saveforfuture.model.CustomerProfileDetail;
import com.saving.saveforfuture.model.Profile;
import com.saving.saveforfuture.model.ProfileResponse;
import com.saving.saveforfuture.model.SavingDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

@Repository
@Slf4j
public class CustomerRepository {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<CustomerProfileDetail> getCustomerFinancialDetail(String customerId) {
        StringJoiner sql = new StringJoiner(" ")
                .add("SELECT")
                .add("a.monthlyexpense,a.monthlyincome,a.memberno,a.tax,c.balance,a.expectage,a.ageofretirement,b.monthlysave")
                .add("FROM")
                .add("customer a")
                .add("LEFT")
                .add("JOIN")
                .add("saving b")
                .add("ON")
                .add("a.customerid=b.customerid")
                .add("LEFT")
                .add("JOIN")
                .add("bank c")
                .add("ON")
                .add("a.customerid=c.customerid")
                .add("WHERE")
                .add("a.customerid=:customerid")
                .add("");

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
                .addValue("customerid",customerId);


        return namedParameterJdbcTemplate.query(sql.toString(),mapSqlParameterSource,new CustomerMapper());

    }

    public List<Profile> getCustomerProfile(String customerId){
        StringJoiner sql = new StringJoiner(" ")
                .add("SELECT")
                .add("a.customername,a.age,a.gender,a.email,a.bankaccno,b.bankname,b.bankid")
                .add("FROM")
                .add("customer a")
                .add("LEFT")
                .add("JOIN")
                .add("bank b")
                .add("ON")
                .add("a.customerid=b.customerid")
                .add("AND")
                .add("a.bankaccno=b.bankaccno")
                .add("WHERE")
                .add("a.customerid=:customerid")
                .add("");

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
                .addValue("customerid",customerId);

        return namedParameterJdbcTemplate.query(sql.toString(),mapSqlParameterSource,new ProfileMapper());
    }

    public int pathCustomerDetail(BigDecimal monthlyIncome,BigDecimal monthlyExpense,int memberNo,String email,String customerId)throws HttpMessageNotReadableException,InvalidFormatException {
        StringJoiner sql = new StringJoiner(" ")
                .add("UPDATE")
                .add("customer")
                .add("SET")
                .add("monthlyincome=:monthlyincome,monthlyexpense=:monthlyexpense,memberno=:memberno,email=:email")
                .add("WHERE")
                .add("customerid=:customerid");
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
                .addValue("monthlyincome",monthlyIncome)
                .addValue("monthlyexpense",monthlyExpense)
                .addValue("memberno",memberNo)
                .addValue("email",email)
                .addValue("customerid",customerId);
        return namedParameterJdbcTemplate.update(sql.toString(),mapSqlParameterSource);
    }



}

