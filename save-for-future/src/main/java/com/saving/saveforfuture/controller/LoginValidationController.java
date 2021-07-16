package com.saving.saveforfuture.controller;

import com.saving.saveforfuture.model.LoginValidationRequest;
import com.saving.saveforfuture.model.LoginValidationResponse;
import com.saving.saveforfuture.service.LoginValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
public class LoginValidationController {
    @Autowired
    private LoginValidationService loginValidationService;

    @PostMapping("/v1/save-for-future/login-validation")
    public ResponseEntity<LoginValidationResponse> postLoginValidation(
            @RequestBody LoginValidationRequest request
            ){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(loginValidationService.loginSuccess(request));
    }

}