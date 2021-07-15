package com.saving.saveforfuture.model;


import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public class CustomerUpdateRequest {
    private BigDecimal monthlyIncome;
    private BigDecimal monthlyExpense;
    private int memberNo;
    private String email;
}
