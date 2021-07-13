package com.saving.saveforfuture.Controller;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.saving.saveforfuture.controller.CustomerController;
import com.saving.saveforfuture.model.ProfileResponse;
import com.saving.saveforfuture.model.SavingResponse;
import com.saving.saveforfuture.service.CustomerService;
import org.apache.coyote.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class CustomerControllerTest {
    @Autowired
    private CustomerController customerController;
    @MockBean
    private CustomerService customerService;
    @Autowired
    private MockMvc mockMvc;


    @Test
    public void getFinancialDetailSuccess()throws Exception{
        String url = "/v1/save-for-future/customer-profile-detail";
        UriComponents uriComponents = UriComponentsBuilder.fromUriString(url).build();
        ObjectMapper objectMapper = new ObjectMapper();
        Resource expectedResource = new ClassPathResource("response/GET_v1_save-for-future_customer-profile-detail.json");
        SavingResponse expected = objectMapper.readValue(expectedResource.getFile(), new TypeReference<SavingResponse>() {
        });
        when(customerService.getCustomerFinancialDetail(any())).thenReturn(expected);
        mockMvc.perform(
                get(uriComponents.toString())
                .param("customerId","customerId"))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    public void getCustomerDetailSuccess()throws Exception{
        String url = "/v1/save-for-future/customer-profile";
        UriComponents uriComponents = UriComponentsBuilder.fromUriString(url).build();
        ObjectMapper objectMapper = new ObjectMapper();
        Resource expectedResource = new ClassPathResource("response/GET_v1_save-for-future_customer-profile.json");
        ProfileResponse expected = objectMapper.readValue(expectedResource.getFile(), new TypeReference<ProfileResponse>() {
        });
        when(customerService.getCustomerProfile(any())).thenReturn(expected);
        mockMvc.perform(
                get(uriComponents.toString())
                        .param("customerId","customerId"))
                .andDo(print())
                .andExpect(status().isOk());

    }

    public static String objectToJson(Object object) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            //log.error("Exception", e);
            return null;
        }
    }
}
