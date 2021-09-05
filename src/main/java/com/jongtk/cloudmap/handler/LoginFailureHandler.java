package com.jongtk.cloudmap.handler;

import com.jongtk.cloudmap.dto.AuthMemberDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;

@Log4j2
public class LoginFailureHandler implements AuthenticationFailureHandler {

    private DefaultRedirectStrategy redirectStrategy = new DefaultRedirectStrategy();


    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        log.warn("로그인 실패 핸들러 실행됨");
        if(exception.getMessage().equals("[ALREADY_REGISTERED_EMAIL] ")){
            redirectStrategy.sendRedirect(request,response,"/sign/oauthfail?result=isLocal");
        } else {
            redirectStrategy.sendRedirect(request,response,"/sign/oauthfail");
        }
    }
}
