package com.jongtk.cloudmap.config;


import com.jongtk.cloudmap.handler.LoginSuccessHandler;
import com.jongtk.cloudmap.service.UserDetailsService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
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
                .antMatchers("/sample/main").hasRole("USER")
                .antMatchers("/sample/admin").hasRole("ADMIN");
        http.formLogin().loginPage("/sign").loginProcessingUrl("/login").failureUrl("/sign/fail");
        http.logout().logoutSuccessUrl("/sample/").invalidateHttpSession(true).deleteCookies();
        http.csrf().disable();
        http.oauth2Login().successHandler(successHandler());
        http.rememberMe().tokenValiditySeconds(60*60*24*7).userDetailsService(userDetailsService);
    }

    @Bean
    public LoginSuccessHandler successHandler(){
        return new LoginSuccessHandler(passwordEncoder());
    }

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication().withUser("user1").password("$2a$10$g2KYQlYxYFP3vHr5jmrFpeWNkWIZ1HwCIE9ugneaN7vQ1UdMT8T8K").roles("USER");
//    }

}
