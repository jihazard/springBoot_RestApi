package com.yooonji.www.accounts;

import com.yooonji.www.common.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
public class AccountController {

    @Autowired
    private AccountService service;

    @Autowired
    private AccountRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    @RequestMapping(value = "/accounts", method = RequestMethod.POST)
    public ResponseEntity create(@RequestBody @Valid AccountDto.Create create
            , BindingResult result) {



        if (result.hasErrors()) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setMessage("잘못된 요청입니다.");
            errorResponse.setCode("bad Request");
             return new ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST);
        }
        Account account = service.createAccount(create);
        return new ResponseEntity<>(modelMapper.map(account, AccountDto.Response.class)
                , HttpStatus.CREATED);

    }

    @ExceptionHandler(UserDuplicationException.class)
    public ResponseEntity handleUserDuplicatedException(UserDuplicationException e) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(e.getUsername()
                + "중복 아이디 입니다.");
        errorResponse.setCode("duplicated username.exception");
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/accounts", method = RequestMethod.GET)
    public ResponseEntity getAccounts(Pageable pageable) {
        Page<Account> page = repository.findAll(pageable);
        List<AccountDto.Response> content = page.getContent().parallelStream()
                .map(account -> modelMapper.map(account, AccountDto.Response.class))
                .collect(Collectors.toList());

        PageImpl<AccountDto.Response> result = new PageImpl<>(content, pageable, page.getTotalPages());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
