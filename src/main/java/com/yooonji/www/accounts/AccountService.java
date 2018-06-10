package com.yooonji.www.accounts;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional
@Slf4j
public class AccountService {
    @Autowired
    AccountRepository repository;

    @Autowired
    ModelMapper modelMapper;


    public Account createAccount(AccountDto.Create create) {

        Account account = modelMapper.map(create, Account.class);

        /*    BeanUtils.copyProperties(create,account);*/
        String username = account.getUserName();
        if (repository.findByUserName(username) != null) {
            log.error("user duplicated exception {} " , username);
            throw new UserDuplicationException(username);
        }

        Date now = new Date();
        account.setJoined(now);
        account.setUpdated(now);

        return repository.save(account);
    }
}
