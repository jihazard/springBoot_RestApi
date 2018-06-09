package com.yooonji.www.accounts;

import com.yooonji.www.ErrorResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class AccountController {

    @Autowired
    private AccountService service;

    @Autowired
    private ModelMapper modelMapper;

    @RequestMapping(value = "/accounts", method = RequestMethod.POST)
    public ResponseEntity create(@RequestBody @Valid AccountDto.Create create
            , BindingResult result) {

        System.out.println("----접속1");

        if (result.hasErrors()) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setMessage("잘못된 요청입니다.");
            errorResponse.setCode("bad Request");
            System.out.println("----400에러 발생");
            return new ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST);
        }
        Account account = service.createAccount(create);
        return new ResponseEntity<>(modelMapper.map(account, AccountDto.Response.class)
                , HttpStatus.CREATED);

    }

   @ExceptionHandler(UserDuplicationException.class)
    public ResponseEntity handleUserDuplicatedException(UserDuplicationException e){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(e.getUsername()
                + "중복 아이디 입니다.");
        errorResponse.setCode("duplicated username.exception");
        return new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST);
    }
}
