package com.saving.saveforfuture.Mapper;


import com.saving.saveforfuture.model.Profile;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.Assert.*;
import static org.hamcrest.MatcherAssert.assertThat;


@SpringBootTest
@RunWith(SpringRunner.class)
public class ProfileMapperTest {
    private ProfileMapper profileMapper = new ProfileMapper();

    @Test
    public void profileMappingTest()throws SQLException {
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getString("customername")).thenReturn("John");
        when(resultSet.getInt("age")).thenReturn(30);
        when(resultSet.getString("bankname")).thenReturn("KrungThai");
        when(resultSet.getString("bankid")).thenReturn("A2");
        when(resultSet.getString("gender")).thenReturn("male");
        when(resultSet.getString("email")).thenReturn("earn@hotmail.com");
        when(resultSet.getString("bankaccno")).thenReturn("4563-122");

        Profile profile = profileMapper.mapRow(resultSet,0);
        assertNotNull(profile);
        assertThat(profile.getCustomerName(), Matchers.equalTo("John"));
        assertThat(profile.getAge(),Matchers.equalTo(30));
        assertThat(profile.getBankAccNo(),Matchers.equalTo("4563-122"));
        assertThat(profile.getBankName(),Matchers.equalTo("KrungThai"));
        assertThat(profile.getBankId(),Matchers.equalTo("A2"));
        assertThat(profile.getGender(),Matchers.equalTo("male"));
        assertThat(profile.getEmail(),Matchers.equalTo("earn@hotmail.com"));
    }
}
