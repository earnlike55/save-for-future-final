package com.saving.saveforfuture.model;


import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class LoginValidationResponse {
    private boolean status;
    private String description;
    private long customerId;
}
