package com.saving.saveforfuture.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

@Data
@Accessors(chain = true)
public class SavingResponse {
    private BigDecimal monthlyIncome;
    private BigDecimal monthlyExpense;
    private BigDecimal tax;
    private BigDecimal suggestAmount;
    private BigDecimal remainingAmount;
    private BigDecimal remainingPercent;
    private BigDecimal savePercent;
    private List<SavingTransaction> savingTransactions;

}
