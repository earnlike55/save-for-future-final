package com.saving.saveforfuture.model;


import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CustomerInsertResponse {
    private int status;
    private String description;
}
