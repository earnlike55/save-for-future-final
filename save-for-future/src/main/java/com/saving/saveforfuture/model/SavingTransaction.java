package com.saving.saveforfuture.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.ZonedDateTime;

@Data
@Accessors(chain = true)
public class SavingTransaction {
    private long savingId;
    private Date createdDateTime;
    private BigDecimal depositAmount;
}
