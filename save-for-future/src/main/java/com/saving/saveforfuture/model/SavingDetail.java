package com.saving.saveforfuture.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.ZonedDateTime;

@Data
@Accessors(chain = true)
public class SavingDetail {
    private String savingId;
    private String customerId;
    private BigDecimal suggestamt;
    private BigDecimal depositamt;
    private Date dateTime;
    private BigDecimal remianingamt;
}
