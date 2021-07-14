package com.saving.saveforfuture.controller;


import com.saving.saveforfuture.model.BankLinkRequest;
import com.saving.saveforfuture.model.BankLinkResponse;
import com.saving.saveforfuture.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
public class BankController {
    @Autowired
    private CustomerService customerService;

    @PatchMapping("/v1/save-for-future/update-bank")
    @CrossOrigin(origins = "http://localhost:8082")
    public ResponseEntity<BankLinkResponse> patchBankDetail(
            @RequestBody BankLinkRequest bankLinkRequest) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(customerService.patchBankDetail(bankLinkRequest.getEmail(),bankLinkRequest.getAccNo()));
    }
}

