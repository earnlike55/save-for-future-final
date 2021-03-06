package com.saving.saveforfuture.Repository;

import com.saving.saveforfuture.Mapper.LoginValidationMapper;
import com.saving.saveforfuture.model.LoginValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

@Repository
public class LoginValidationRepository {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public LoginValidation selectEmailPassword(String email, String password){
        StringJoiner sql = new StringJoiner(" ")
                .add("SELECT")
                .add("email,password,salt,customerid")
                .add("FROM")
                .add("customer")
                .add("WHERE")
                .add("email=:email")
                .add(" ");
        LoginValidation loginValidationList;
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
                .addValue("email",email)
                .addValue("password",password);
        try{
            loginValidationList = namedParameterJdbcTemplate.queryForObject(sql.toString(),mapSqlParameterSource,new LoginValidationMapper());
            System.err.println(loginValidationList);
            System.err.println(sql);
            System.err.println(mapSqlParameterSource);
       }
       catch (EmptyResultDataAccessException e){
           return null;
      }
        return loginValidationList;


    }

    public String getSalt(String email){
        StringJoiner sql = new StringJoiner(" ")
                .add("SELECT")
                .add("salt")
                .add("FROM")
                .add("customer")
                .add("WHERE")
                .add("email=:email");
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
                .addValue("email",email);
        try{
            namedParameterJdbcTemplate.queryForObject(sql.toString(),mapSqlParameterSource,String.class);;
        }
        catch (EmptyResultDataAccessException e){
            return null;
        }


        return  namedParameterJdbcTemplate.queryForObject(sql.toString(),mapSqlParameterSource,String.class);
    }

}
