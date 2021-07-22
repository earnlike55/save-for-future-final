package com.saving.saveforfuture.Repository;

import com.saving.saveforfuture.Mapper.CustomerMapper;
import com.saving.saveforfuture.Mapper.SavingMapper;
import com.saving.saveforfuture.model.BankDetail;
import com.saving.saveforfuture.model.SavingDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.StringJoiner;

@Repository
public class SavingRepository {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<SavingDetail> getSavingDetail(long customerId){
        StringJoiner sql = new StringJoiner(" ")
                .add("SELECT")
                .add("monthlysave,createdatetime,savingid")
                .add("FROM")
                .add("saving")
                .add("WHERE")
                .add("customerid=:customerid");
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
                .addValue("customerid",customerId);
        return namedParameterJdbcTemplate.query(sql.toString(),mapSqlParameterSource,new SavingMapper());

    }

    public int updateBalance(long customerId, BigDecimal balance){
        StringJoiner sql = new StringJoiner(" ")
                .add("UPDATE")
                .add("bank")
                .add("SET")
                .add("balance=:balance")
                .add("WHERE")
                .add("customerid=:customerid");
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
                .addValue("customerid",customerId)
                .addValue("balance",balance);
        return namedParameterJdbcTemplate.update(sql.toString(),mapSqlParameterSource);
    }

    public int selectFlag(long customerId){
        StringJoiner sql = new StringJoiner(" ")
                .add("SELECT")
                .add("flag")
                .add("FROM")
                .add("bank")
                .add("WHERE")
                .add("customerid=:customerid");

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
                .addValue("customerid",customerId);
        return namedParameterJdbcTemplate.queryForObject(sql.toString(),mapSqlParameterSource,Integer.class);
    }

    public int updateFlag(long customerId,int flag){
        StringJoiner sql = new StringJoiner(" ")
                .add("UPDATE")
                .add("bank")
                .add("SET")
                .add("flag=:flag")
                .add("WHERE")
                .add("customerid=:customerid");
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
                .addValue("flag",flag)
                .addValue("customerid",customerId);
        return namedParameterJdbcTemplate.update(sql.toString(),mapSqlParameterSource);
    }
}
