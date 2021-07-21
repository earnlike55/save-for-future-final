package com.saving.saveforfuture.model;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * Created by s60968 on 11/29/2016.
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class ResponseStatus implements Serializable {
    private Integer code;
    private String header;
    private String description;

    public ResponseStatus(Integer code){
        this.code = code;
    }

    public ResponseStatus(Integer code, String description){
        this.code = code;
        this.description = description;
    }
}

