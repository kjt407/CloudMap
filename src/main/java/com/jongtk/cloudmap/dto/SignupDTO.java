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
@Setter
@ToString
public class SignupDTO {

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String email1;
    @NotBlank
    private String email2;

    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,20}$")
    @NotBlank
    private String password;
    private String passwordCheck;


    @NotBlank
    private String name;

    @Max(13)
    @NotBlank
    private String phone;

//    private final boolean fromSocial = false;

    public SignupDTO(String email1, String email2, String password, String passwordCheck, String name, String phone) {
        this.email = email1+"@"+email2;
        this.email1 = email1;
        this.email2 = email2;
        this.password = password;
        this.passwordCheck = passwordCheck;
        this.name = name;
        this.phone = phone;
    }
}
