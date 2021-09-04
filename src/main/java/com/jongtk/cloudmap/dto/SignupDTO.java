package com.jongtk.cloudmap.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import javax.validation.constraints.*;
import java.util.Collection;
import java.util.Map;

@Log4j2
@Getter
@ToString
public class SignupDTO {

    @Email(message = "올바른 Email 형식이 아닙니다.")
    @NotBlank(message = "이메일은 공백을 포함할 수 없습니다.")
    private String email;

    @NotBlank
    private String email1;
    @NotBlank
    private String email2;

    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,20}$", message = "비밀번호는 문자,숫자,특수문자를 포함해야합니다.")
    @NotBlank(message = "비밀번호는 공백을 포함할 수 없습니다.")
    private String password;
    @NotBlank(message = "비밀번호가 일치하지 않습니다.")
    private String passwordCheck;


    @NotBlank(message = "이름은 공백을 포함할 수 없습니다.")
    private String name;

    @Pattern(regexp = "^\\d{3}-\\d{3,4}-\\d{4}$", message = "올바르지 않은 전화번호입니다.")
    @NotBlank(message = "전화번호는 공백을 포함할 수 없습니다.")
    private String phone;


    public SignupDTO(String email1, String email2, String password, String passwordCheck, String name, String phone) {
        log.warn(email1+email2+password+passwordCheck+name+phone);
        this.email = email1+"@"+email2;
        this.email1 = email1;
        this.email2 = email2;
        this.password = password;
        if(password.equals(passwordCheck)) {
            this.passwordCheck = passwordCheck;
        }
        this.name = name;
        this.phone = phone;
    }
}
