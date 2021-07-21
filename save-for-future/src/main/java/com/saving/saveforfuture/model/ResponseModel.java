package com.saving.saveforfuture.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Created by s60968 on 11/3/2016.
 */
@Getter
@Setter
@Accessors(chain = true)
public class ResponseModel<T> extends BaseResponseObject {

    public ResponseModel(){
        super();
    }

    public ResponseModel(Integer code){
        super(code);
    }

    public ResponseModel(Integer code, String description){
        super(code, description);
    }

    public ResponseModel(Integer code, String header, String description){
        super(code, header, description);
    }

    public ResponseModel(ResponseStatus responseStatus){
        super(responseStatus);
    }

    @Override
    public ResponseModel<T> setCode(Integer code){
        super.setCode(code);
        return this;
    }

    @Override
    public ResponseModel<T> setDescription(String description){
        super.setDescription(description);
        return this;
    }

    @Override
    public ResponseModel<T> setHeader(String header){
        super.setHeader(header);
        return this;
    }

    @Override
    public ResponseModel<T> setStatus(ResponseStatus responseStatus){
        super.setStatus(responseStatus);
        return this;
    }

    /**
     * Generic Type for diversity response obj.
     */
    @JsonProperty(value = "data")
    @SuppressWarnings("squid:S1948")
    private T dataObj;
}
