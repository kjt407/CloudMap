package com.jongtk.cloudmap.handler;

import com.jongtk.cloudmap.dto.AuthMemberDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j2
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    private DefaultRedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    private PasswordEncoder passwordEncoder;

    public LoginSuccessHandler(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("---------------------");
        log.info("로그인 성공 핸들러 실행됨");

        AuthMemberDTO authMember = (AuthMemberDTO) authentication.getPrincipal();

        log.info(authMember);

        boolean fromSocial = authMember.isFromSocial();

        log.info("password: "+authMember.getPassword());
        boolean isEdited = passwordEncoder.matches("1111", authMember.getPassword());

        if(fromSocial && isEdited){
            redirectStrategy.sendRedirect(request,response,"/");
        } else {
            redirectStrategy.sendRedirect(request,response,"/");
        }
    }
}
