package com.saving.saveforfuture.Repository;

import com.saving.saveforfuture.Mapper.CustomerMapper;
import com.saving.saveforfuture.Mapper.SavingMapper;
import com.saving.saveforfuture.model.BankDetail;
import com.saving.saveforfuture.model.SavingDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

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
}
