package com.saving.saveforfuture.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Profile {
    private String customerName;
    private int age;
    private String bankName;
    private String bankId;
    private String gender;
    private String email;
    private String bankAccNo;
}
