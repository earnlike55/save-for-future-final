package com.saving.saveforfuture.controller;

import com.saving.saveforfuture.model.SavingResponse;
import com.saving.saveforfuture.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@Slf4j
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @GetMapping("/v1/save-for-future/customer-profile-detail")
    public ResponseEntity<SavingResponse> getCustomerFinancialDetail(
            @RequestParam(required = false) String customerId
            ){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(customerService.getCustomerFinancialDetail(customerId));
    }

}
