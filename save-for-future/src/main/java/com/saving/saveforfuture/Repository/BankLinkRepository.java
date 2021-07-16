package com.saving.saveforfuture.Repository;

import com.saving.saveforfuture.Mapper.BankMapper;
import com.saving.saveforfuture.Mapper.CustomerMapper;
import com.saving.saveforfuture.Mapper.ProfileMapper;
import com.saving.saveforfuture.model.CustomerProfileDetail;
import com.saving.saveforfuture.model.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.print.DocFlavor;
import java.util.List;
import java.util.StringJoiner;

@Repository
public class BankLinkRepository {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int updateBankDetail(long customerId,String bankAccNo){
        StringJoiner sql = new StringJoiner(" ")
                .add("UPDATE")
                .add("customer")
                .add("SET")
                .add("bankaccno=:bankaccno")
                .add("WHERE")
                .add("customerid=:customerid");

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
                .addValue("customerid",customerId)
                .addValue("bankaccno",bankAccNo);
        return namedParameterJdbcTemplate.update(sql.toString(),mapSqlParameterSource);
    }

    public long getCustomerIdFromEmail(String email){
        StringJoiner sql = new StringJoiner(" ")
                .add("SELECT")
                .add("a.customerid")
                .add("FROM")
                .add("customer a")
                .add("WHERE")
                .add("email=:email");

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
                .addValue("email",email);
        long emails = 0;
        try{
             emails =  namedParameterJdbcTemplate.queryForObject(sql.toString(),mapSqlParameterSource,long.class);
        }
        catch(EmptyResultDataAccessException e){
            return 0;
        }
        return emails;
    }

    public String getCustomerBankAccNo(String bankAccNo){
        StringJoiner sql = new StringJoiner(" ")
                .add("SELECT")
                .add("bankaccno")
                .add("FROM")
                .add("bank")
                .add("WHERE")
                .add("bankaccno=:bankaccno")
                .add(" ");
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
                .addValue("bankaccno",bankAccNo);
        String bankAcc = "";
        try{
            bankAcc = namedParameterJdbcTemplate.queryForObject(sql.toString(),mapSqlParameterSource,String.class);
        }
        catch (EmptyResultDataAccessException e){
            return null;
        }
        return bankAcc;

    }
}
