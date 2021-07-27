package com.saving.saveforfuture.Service;

import com.saving.saveforfuture.model.LoginValidationRequest;
import com.saving.saveforfuture.model.LoginValidationResponse;
import org.hamcrest.Matchers;
import com.saving.saveforfuture.Repository.LoginValidationRepository;
import com.saving.saveforfuture.model.LoginValidation;
import com.saving.saveforfuture.service.LoginValidationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
import static org.hamcrest.MatcherAssert.assertThat;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;


@SpringBootTest
@RunWith(SpringRunner.class)


public class LoginValidationServiceTest {

    @Autowired
    private LoginValidationService loginValidationService;
    @MockBean
    private LoginValidationRepository loginValidationRepository;

    @Test
    public void loginTestSuccess(){
        LoginValidation loginValidation = new LoginValidation()
                .setEmail("Samie@hotmail.com")
                .setSalt("0dqYku9eG5gZetWRewL3G5nGBURyPz")
                .setCustomerId(10L)
                .setPassword("rfcICg1B8RGPeXqiu8v0mwft2hMcS6tbymb5FKlIg94=");
        LoginValidationRequest loginValidationRequest = new LoginValidationRequest()
                .setEmail("Samie@hotmail.com")
                .setPassword("earnearn55");
        when(loginValidationRepository.getSalt(anyString())).thenReturn("0dqYku9eG5gZetWRewL3G5nGBURyPz");
        when(loginValidationRepository.selectEmailPassword(anyString(),anyString())).thenReturn(loginValidation);
        LoginValidationResponse response;
        response = loginValidationService.loginSuccess(loginValidationRequest);
        assertThat(response.getDescription(),Matchers.equalTo("Success"));
        assertThat(response.getStatus(),Matchers.equalTo(1000));
    }

    @Test
    public void loginTestFailWrongEmail(){
        LoginValidation loginValidation = new LoginValidation()
                .setEmail("Samie1@hotmail.comn")
                .setSalt("0dqYku9eG5gZetWRewL3G5nGBURyPz")
                .setCustomerId(10L)
                .setPassword("rfcICg1B8RGPeXqiu8v0mwft2hMcS6tbymb5FKlIg94=");
        LoginValidationRequest loginValidationRequest = new LoginValidationRequest()
                .setEmail("Samie4@hotmail.com")
                .setPassword("earnearn55");
        when(loginValidationRepository.getSalt(anyString())).thenReturn(null);
        when(loginValidationRepository.selectEmailPassword(anyString(),anyString())).thenReturn(loginValidation);
        LoginValidationResponse response;
        response = loginValidationService.loginSuccess(loginValidationRequest);
        assertThat(response.getDescription(),Matchers.equalTo("Wrong email"));
        assertThat(response.getStatus(),Matchers.equalTo(1999));
    }

    @Test
    public void loginTestFailWrongPassword(){
        LoginValidation loginValidation = new LoginValidation()
                .setEmail("Samie@hotmail.comn")
                .setSalt("0dqYku9eG5gZetWRewL3G5nGBURyPz")
                .setCustomerId(10L)
                .setPassword("rfcICg1B8RGPeXqiu8v0mwft2hMcS6tbymb5FKlIg94");
        LoginValidationRequest loginValidationRequest = new LoginValidationRequest()
                .setEmail("Samie@hotmail.com")
                .setPassword("earnearn55");
        when(loginValidationRepository.getSalt(anyString())).thenReturn("0dqYku9eG5gZetWRewL3G5nGBURyPz");
        when(loginValidationRepository.selectEmailPassword(anyString(),anyString())).thenReturn(loginValidation);
        LoginValidationResponse response;
        response = loginValidationService.loginSuccess(loginValidationRequest);
        assertThat(response.getDescription(),Matchers.equalTo("Wrong password"));
        assertThat(response.getStatus(),Matchers.equalTo(1999));
    }


}
