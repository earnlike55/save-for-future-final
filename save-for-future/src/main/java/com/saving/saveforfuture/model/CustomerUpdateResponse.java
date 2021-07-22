package com.saving.saveforfuture.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CustomerUpdateResponse {
    private int status;
    private String description;
}
