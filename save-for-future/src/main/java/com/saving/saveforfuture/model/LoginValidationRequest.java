package com.saving.saveforfuture.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class LoginValidationRequest {
    private String email;
    private String password;
}
