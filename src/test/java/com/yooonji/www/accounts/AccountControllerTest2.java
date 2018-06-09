
package com.yooonji.www.accounts;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yooonji.www.Application;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@Transactional
public class AccountControllerTest2{

    @Autowired
    WebApplicationContext wac;

    @Autowired
    ObjectMapper mapper;


    @Test
    public void Create() throws Exception{
        MockMvc mvc = MockMvcBuilders.webAppContextSetup(wac)
                .build();
        AccountDto.Create create = new AccountDto.Create();
        create.setUserName("yoonjh238");
        create.setPassword("11111111");

        ResultActions action = mvc.perform(post("/accounts")
        .contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(create)));

        action.andDo(print());
        action.andExpect(status().isCreated());

    }

}