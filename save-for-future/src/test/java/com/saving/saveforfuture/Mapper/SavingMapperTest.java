package com.saving.saveforfuture.Mapper;

import com.saving.saveforfuture.model.SavingDetail;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.hamcrest.Matchers;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.Assert.*;
import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)

public class SavingMapperTest {

    private SavingMapper savingMapper = new SavingMapper();

    @Test
    public void savingMappingTest()throws SQLException,Exception{
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getLong("savingid")).thenReturn(1L);
        when(resultSet.getDate("createdatetime")).thenReturn(Date.valueOf("1999-05-05"));
        when(resultSet.getBigDecimal("monthlysave")).thenReturn(new BigDecimal(3000));

        SavingDetail savingDetail = savingMapper.mapRow(resultSet,0);
        assertNotNull(savingDetail);
        assertThat(savingDetail.getSavingId(),Matchers.equalTo(1L));
        assertEquals(0,savingDetail.getDateTime().compareTo(Date.valueOf("1999-05-05")));
        assertEquals(0,savingDetail.getDepositamt().compareTo(new BigDecimal(3000)));

    }
}
