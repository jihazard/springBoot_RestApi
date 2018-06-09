package com.yooonji.www.accounts;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional
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
            throw new UserDuplicationException(username);
        }

        Date now = new Date();
        account.setJoined(now);
        account.setUpdated(now);

        return repository.save(account);
    }
}
