package com.saving.saveforfuture.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZonedDateTime;

@Data
@Accessors(chain = true)
public class CustomerProfileDetail {
    private long customerId;
    private String email;
    private Date dob;
    private int age;
    private int memberno;
    private BigDecimal monthlyIncome;
    private BigDecimal monthlyExpense;
    private BigDecimal tax;
    private int ageOfRetirement;
    private String password;
    private long savingId;
    private String gender;
    private int expectAge;
    private BigDecimal suggestAmount;
    private BigDecimal balance;
}
