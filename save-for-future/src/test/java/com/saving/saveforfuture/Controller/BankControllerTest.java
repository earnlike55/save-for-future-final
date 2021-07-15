package com.saving.saveforfuture.Controller;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.saving.saveforfuture.controller.BankController;
import com.saving.saveforfuture.model.BankLinkRequest;
import com.saving.saveforfuture.model.BankLinkResponse;
import com.saving.saveforfuture.model.SavingResponse;
import com.saving.saveforfuture.service.CustomerService;
import org.checkerframework.checker.units.qual.A;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Objects;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class BankControllerTest {
    @MockBean
    private CustomerService customerService;
    @Autowired
    private BankController bankController;
    @Autowired
    private MockMvc mockMvc;


    @Test
    public void patchBankSuccess() throws Exception {
        String url = "/v1/save-for-future/update-bank";
        UriComponents uriComponents = UriComponentsBuilder.fromUriString(url).build();
        ObjectMapper objectMapper = new ObjectMapper();
        Resource expectedResource = new ClassPathResource("response/PATCH_v1_save-for-future_update-bank.json");
        BankLinkResponse expected = objectMapper.readValue(expectedResource.getFile(), new TypeReference<BankLinkResponse>() {
        });
        when(customerService.patchBankDetail(anyString(), anyString())).thenReturn(new BankLinkResponse());
        BankLinkRequest request = new BankLinkRequest()
                .setEmail("aabb")
                .setAccNo("123-456");
        mockMvc.perform(
                patch(uriComponents.toUriString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Objects.requireNonNull(objectToJson(request))))
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
