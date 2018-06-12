package com.yooonji.www.accounts;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yooonji.www.Application;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@Transactional
public class AccountControllerTest {
    @Autowired
    WebApplicationContext wac;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    AccountService service;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .build();
    }

    @Test
    public void create() throws Exception {
        AccountDto.Create accountDto = new AccountDto.Create();
        accountDto.setUserName("psyche2823");
        accountDto.setPassword("123456");
        ResultActions result = mockMvc.perform(post("/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(accountDto)));

        result.andDo(print());
        result.andExpect(status().isCreated());
        result.andExpect(jsonPath("$.userName", is("psyche2823")));

        //아이디 중복 테스트
        result = mockMvc.perform(post("/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(accountDto)));

        result.andDo(print());
        result.andExpect(status().isBadRequest());
        result.andExpect(jsonPath("$.code", is("duplicated username.exception")));
    }

    @Test
    public void createAcount_BadRequest() throws Exception {
        AccountDto.Create accountDto = new AccountDto.Create();
        accountDto.setUserName(" ");
        accountDto.setPassword("123456");

        ResultActions result = mockMvc.perform(post("/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(accountDto)));

        result.andDo(print());
        result.andExpect(status().isBadRequest());
    }

    @Test
    public void getAccount() throws Exception {
        AccountDto.Create createDto = accountCreateFixture();
        service.createAccount(createDto);

        ResultActions result = mockMvc.perform(get("/accounts"));
        result.andDo(print());
        result.andExpect(status().isOk());
    }

    private AccountDto.Create accountCreateFixture() {
        AccountDto.Create createDto = new AccountDto.Create();
        createDto.setUserName("psyche2823");
        createDto.setPassword("12345678");
        return createDto;
    }

    @Test
    public void getAccounts() throws Exception{
        AccountDto.Create accountDto = accountCreateFixture();
        Account account = service.createAccount(accountDto);

        ResultActions result = mockMvc.perform(get("/accounts/"+account.getId()));
        result.andDo(print());
        result.andExpect(status().isOk());
    }

    @Test
    public void accountUpdate() throws Exception {
        AccountDto.Create accountDto = accountCreateFixture();
        Account account = service.createAccount(accountDto);

        AccountDto.UPDATE updateAccount = new AccountDto.UPDATE();
        updateAccount.setFullName("yoon33153315");
        updateAccount.setPassword("9872954");

        ResultActions result = mockMvc.perform(put("/accounts/"+account.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(updateAccount)));

        result.andDo(print());
        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.fullName", is("yoon33153315")));
        result.andExpect(jsonPath("$.password", is("9872954")));


    }
}