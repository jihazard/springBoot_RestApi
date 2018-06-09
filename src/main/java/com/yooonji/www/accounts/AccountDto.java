package com.yooonji.www.accounts;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

public class AccountDto {
    @Data
    public static class Create {
        @NotBlank
        @Size(min = 5)
        private String userName;
        @NotBlank
        @Size(min = 5)
        private String password;
    }

    @Data
    public static class Response{

        private Long id;
        private String userName;
        @JsonIgnore
        private String password;
        private String fullName;
        private Date joined;
        private Date updated;

    }

}
