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
            List<LoginValidation> loginValidationList = loginValidationRepository.selectEmailPassword(
                    request.getEmail(),
                    securePass);
            if (loginValidationList != null) {

                boolean passwordMatch;
                String getSecurePassword = "";
                try {
                    getSecurePassword = loginValidationList.get(0).getPassword();
                } catch (IndexOutOfBoundsException e) {
                    response.setDescription("Wrong password");
                    response.setStatus(false);
                    return response;
                }

                passwordMatch = PasswordUtils.verifyUserPassword(
                        request.getPassword(),
                        getSecurePassword,
                        salts);
                if (loginValidationList.get(0).getEmail().equals(request.getEmail()) &&
                        passwordMatch == true) {
                    response.setDescription("Success");
                    response.setStatus(true);
                    response.setCustomerId(loginValidationList.get(0).getCustomerId());
                }

                return response;
            }
        }
        response.setDescription("Wrong email");
        response.setStatus(false);
        return response;
    }
}
