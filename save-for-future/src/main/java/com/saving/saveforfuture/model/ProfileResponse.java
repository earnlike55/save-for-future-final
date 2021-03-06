package com.saving.saveforfuture.model;


import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public class ProfileResponse {
    private String customerName;
    private int age;
    private long bankId;
    private String bankName;
    private String bankAccNo;
    private String Gender;
    private String email;
    private BigDecimal monthlyIncome;
    private BigDecimal monthlyExpense;
    private int memberNo;
    private BigDecimal suggestMonthly;
    private int status;
    private String description;
}
