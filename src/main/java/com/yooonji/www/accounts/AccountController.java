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

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@Slf4j
public class AccountController {

    @Autowired
    private AccountService service;

    @Autowired
    private AccountRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    @RequestMapping(value = "/accounts", method = POST)
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
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleUserDuplicatedException(UserDuplicationException e) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(e.getUsername()
                + "중복 아이디 입니다.");
        errorResponse.setCode("duplicated username.exception");
        return errorResponse;
    }

    @ExceptionHandler(AccountNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse accountNotFoundException(AccountNotFoundException e){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage("["+e.getId()+"]에 대한 오류입니다. 해당 계정이 없습니다.");
        errorResponse.setCode("Account.Not.Found.Exception");
        return errorResponse;
    }

    @RequestMapping(value = "/accounts", method = GET)
    public ResponseEntity getAccounts(Pageable pageable) {
        Page<Account> page = repository.findAll(pageable);
        List<AccountDto.Response> content = page.getContent().parallelStream()
                .map(account -> modelMapper.map(account, AccountDto.Response.class))
                .collect(Collectors.toList());

        PageImpl<AccountDto.Response> result = new PageImpl<>(content, pageable, page.getTotalPages());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value="/accounts/{id}", method = GET)
    @ResponseStatus(HttpStatus.OK)
    public AccountDto.Response  getAccount(@PathVariable Long id){
        Account account = service.getAccount(id);
       return modelMapper.map(account, AccountDto.Response.class);
    }

    //전체업데이트 PUT VS 부분 업데이트 PATCH이용
    @RequestMapping(value="/accounts/{id}", method = PUT)
    public ResponseEntity updateAccount(@PathVariable Long id, @RequestBody AccountDto.UPDATE updateDto
    ,BindingResult result){

        if(result.hasErrors()){
            log.error("잘못된 업데이트 : {}", result);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        log.debug("받은데이터 : {}" + updateDto);
       Account resultAccount =  service.updateAccound(id , updateDto);
        return new ResponseEntity<>(modelMapper.map(resultAccount, AccountDto.Response.class), HttpStatus.OK);
    }

}
