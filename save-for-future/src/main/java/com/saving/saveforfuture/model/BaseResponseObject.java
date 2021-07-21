package com.saving.saveforfuture.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * Created by s60976 on 22/12/2017.
 */
@Getter
@Setter
@Accessors(chain = true)
public class BaseResponseObject implements Serializable {

    private ResponseStatus status;

    public BaseResponseObject(){
        this(new ResponseStatus());
    }

    public BaseResponseObject(ResponseStatus status) {
        this.status = status;
    }

    public BaseResponseObject(Integer code){
        this(new ResponseStatus(code));
    }

    public BaseResponseObject(Integer code, String description){
        this(new ResponseStatus(code, description));
    }

    public BaseResponseObject(Integer code, String header, String description){
        this(new ResponseStatus(code, header, description));
    }

    @JsonIgnore
    public Integer getCode() {
        return status.getCode();
    }

    public BaseResponseObject setCode(Integer code){
        status.setCode(code);
        return this;
    }

    @JsonIgnore
    public String getHeader() {
        return status.getHeader();
    }

    public BaseResponseObject setHeader(String header){
        status.setHeader(header);
        return this;
    }

    @JsonIgnore
    public String getDescription() {
        return status.getDescription();
    }

    public BaseResponseObject setDescription(String description){
        status.setDescription(description);
        return this;
    }

    @Override
    public String toString() {
        return "{" +
                "status=" + status +
                '}';
    }
}
