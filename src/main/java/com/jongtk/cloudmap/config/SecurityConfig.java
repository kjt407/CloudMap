package com.jongtk.cloudmap.config;


import com.jongtk.cloudmap.handler.LoginFailureHandler;
import com.jongtk.cloudmap.handler.LoginSuccessHandler;
import com.jongtk.cloudmap.service.UserDetailsService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@Log4j2
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/main").hasRole("USER");
        http.formLogin().loginPage("/sign").loginProcessingUrl("/login").failureUrl("/sign/fail").defaultSuccessUrl("/main");
        http.logout().invalidateHttpSession(true).deleteCookies();
        http.csrf().disable();
        http.oauth2Login().successHandler(successHandler()).failureHandler(new LoginFailureHandler());
        http.rememberMe().tokenValiditySeconds(60*60*24*7).userDetailsService(userDetailsService);
    }

    @Bean
    public LoginSuccessHandler successHandler(){
        return new LoginSuccessHandler(passwordEncoder());
    }

}
