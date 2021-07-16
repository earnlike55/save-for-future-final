package com.saving.saveforfuture.Mapper;

import com.saving.saveforfuture.model.SavingDetail;
import org.springframework.jdbc.core.RowMapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class SavingMapper implements RowMapper<SavingDetail> {

    @Override
    public SavingDetail mapRow(ResultSet resultSet, int i) throws SQLException {
        return new SavingDetail()
                .setSavingId(resultSet.getLong("savingid"))
                .setDateTime(resultSet.getDate("createdatetime"))
                .setDepositamt(resultSet.getBigDecimal("monthlysave"));
    }

//    public ZonedDateTime DateTimeToZoneDateTime(Date date){
//        ZoneId defaultZoneId = ZoneId.systemDefault();
//        // Convert Date to Instant.
//        Instant instant = date.toInstant();
//        // Instant + default time zone.
//        ZonedDateTime zonedDateTime = instant.atZone(defaultZoneId);
//        return zonedDateTime;
//    }
}
