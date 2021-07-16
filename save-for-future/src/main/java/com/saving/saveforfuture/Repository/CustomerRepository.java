package com.saving.saveforfuture.Repository;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.saving.saveforfuture.Mapper.CustomerMapper;
import com.saving.saveforfuture.Mapper.ProfileMapper;
import com.saving.saveforfuture.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.print.DocFlavor;
import java.math.BigDecimal;
import java.sql.Date;
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
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @PersistenceContext
    private EntityManager entityManager;


    public List<CustomerProfileDetail> getCustomerFinancialDetail(long customerId) {
        StringJoiner sql = new StringJoiner(" ")
                .add("SELECT")
                .add("a.monthlyexpense,a.monthlyincome,a.memberno,c.balance,a.expectage,a.ageofretirement,b.monthlysave")
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
                .add(" ");

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
                .addValue("customerid",customerId);


        return namedParameterJdbcTemplate.query(sql.toString(),mapSqlParameterSource,new CustomerMapper());

    }

    public List<Profile> getCustomerProfile(long customerId){
        StringJoiner sql = new StringJoiner(" ")
                .add("SELECT")
                .add("a.customername,a.age,a.gender,a.email,a.bankaccno,b.bankname,b.bankid,a.memberno")
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

    public int pathCustomerDetail(BigDecimal monthlyIncome,BigDecimal monthlyExpense,int memberNo,String email,long customerId){
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

    public int postCustomerDetail(CustomerInsertRequest request,int age){
        if(selectEmail(request.getEmail()) == false){
            final String sql = "INSERT INTO customer "+
                    "(customername,email,dob,age,memberno,monthlyincome,monthlyexpense,ageofretirement,gender,expectage,password) "+
                    "VALUES "+
                    "(:customername,:email,:dob,:age,:memberno,:monthlyincome,:monthlyexpense,:ageofretirement,:gender,:expectage,:password);";
            Map<String,Object> namedParameters = new HashMap<>();
            namedParameters.put("customername",request.getCustomerName());
            namedParameters.put("email",request.getEmail());
            namedParameters.put("dob",request.getDob());
            namedParameters.put("age",age);
            namedParameters.put("memberno",request.getMemberNo());
            namedParameters.put("monthlyincome",request.getMonthlyIncome());
            namedParameters.put("monthlyexpense",request.getMonthlyExpense());
            namedParameters.put("ageofretirement",request.getAgeOfRetirement());
            namedParameters.put("gender",request.getGender());
            namedParameters.put("expectage",request.getExpectAge());
            namedParameters.put("password",request.getPassword());
            return namedParameterJdbcTemplate.update(sql,namedParameters);
        }
        else
        {
            return -1;
        }
    }

    public boolean selectEmail(String email){
        StringJoiner sql = new StringJoiner(" ")
                .add("SELECT")
                .add("email")
                .add("FROM")
                .add("customer")
                .add("WHERE")
                .add("email=:email")
                .add(" ");
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
                .addValue("email",email);
        String selected = "";
        try{
             selected = namedParameterJdbcTemplate.queryForObject(sql.toString(),mapSqlParameterSource,String.class);
        }catch(EmptyResultDataAccessException e){
            if(selected.equals(null)|| selected.equals("")){
                return false;
            }
        }


        return true;
    }



}

