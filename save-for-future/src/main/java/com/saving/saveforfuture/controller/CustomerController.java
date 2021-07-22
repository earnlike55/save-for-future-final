package com.saving.saveforfuture.controller;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.saving.saveforfuture.model.*;
import com.saving.saveforfuture.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
@Validated
@Slf4j
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @GetMapping("/v1/save-for-future/customer-profile-detail")
    public ResponseEntity<SavingResponse> getCustomerFinancialDetail(
            @RequestParam(required = false) long customerId
            ){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(customerService.getCustomerFinancialDetail(customerId));
    }

    @GetMapping("/v1/save-for-future/customer-profile")
    public ResponseEntity<ProfileResponse> getCustomerProfileDetail(
            @RequestParam(required = false) long customerId){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(customerService.getCustomerProfile(customerId));
    }

    @PatchMapping("/v1/save-for-future/customer-profile-update")
    public ResponseEntity<CustomerUpdateResponse> updateCustomerProfile(
            @RequestParam(required  = false) long customerId,
            @RequestBody CustomerUpdateRequest request) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(customerService.patchCustomerDetail(customerId,
                            request.getEmail(),
                            request.getMonthlyIncome(),
                            request.getMonthlyExpense(),
                            request.getMemberNo()));
        }

    @PostMapping("/v1/save-for-future/post-customer-detail")
    public ResponseEntity<CustomerInsertResponse> postCustomerProfile(
            @RequestBody CustomerInsertRequest request){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(customerService.postCustomerProfile(request));
    }


    }




