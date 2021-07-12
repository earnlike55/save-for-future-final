package com.saving.saveforfuture.model;

import lombok.Data;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
public class BankDetail {

    private String bankId;
    private String customerId;
    private String accountType;
    private int balance;
    private int interest;
}
