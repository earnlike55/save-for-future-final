package com.saving.saveforfuture.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class LoginValidation {

    private String email;
    private String password;
    private String salt;
    private long customerId;
}
