package com.yooonji.www.accounts;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.util.MatcherAssertionErrors.assertThat;

public class AccountTest {
    @Test
    public void getter() {
        Account account = new Account();
        account.setUserName("psyche2823");
        account.setPassword("1234");
        assertThat(account.getUserName() ,is("psyche2823"));


    }
}