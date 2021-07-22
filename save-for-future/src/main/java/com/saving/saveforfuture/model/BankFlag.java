package com.saving.saveforfuture.model;


import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class BankFlag {
    private int flag;
}
