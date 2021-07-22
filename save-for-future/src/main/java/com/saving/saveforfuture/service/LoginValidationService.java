package com.saving.saveforfuture.service;


import com.saving.saveforfuture.Repository.LoginValidationRepository;
import com.saving.saveforfuture.model.LoginValidation;
import com.saving.saveforfuture.model.LoginValidationRequest;
import com.saving.saveforfuture.model.LoginValidationResponse;
import com.saving.saveforfuture.util.PasswordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginValidationService {
    @Autowired
    private LoginValidationRepository loginValidationRepository;

    public LoginValidationResponse loginSuccess(LoginValidationRequest request) {
        LoginValidationResponse response = new LoginValidationResponse();
        String salts = loginValidationRepository.getSalt(request.getEmail());
        if(salts != null) {
            String securePass = PasswordUtils.generateSecurePassword(request.getPassword(), salts);
            LoginValidation loginValidationList = loginValidationRepository.selectEmailPassword(
                    request.getEmail(),
                    securePass);
            if (loginValidationList != null) {

                boolean passwordMatch = PasswordUtils.verifyUserPassword(
                        request.getPassword(),
                        loginValidationList.getPassword(),
                        salts);
                if(passwordMatch == true) {
                    response.setDescription("Success");
                    response.setStatus(1000);
                    response.setCustomerId(loginValidationList.getCustomerId());
                }
                else {
                    response.setDescription("Wrong password");
                    response.setStatus(1999);
                }


                return response;
            }
        }
        response.setDescription("Wrong email");
        response.setStatus(1999);
        return response;
    }
}
