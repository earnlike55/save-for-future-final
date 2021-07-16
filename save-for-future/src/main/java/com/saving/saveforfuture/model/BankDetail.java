package com.saving.saveforfuture.model;

import lombok.Data;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
public class BankDetail {

    private long bankId;
    private long customerId;
    private String accountType;
    private int balance;
    private int interest;
}
