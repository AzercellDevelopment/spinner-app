package com.spinnerapp.config;

import com.spinnerapp.security.common.JwtAuthFilterConfigurerAdapter;
import com.spinnerapp.security.common.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@Configuration
@EnableWebSecurity
@Slf4j
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final List<AuthService> authServices;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.apply(new JwtAuthFilterConfigurerAdapter(authServices));

        http

                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/auth/**").permitAll()
                .antMatchers(HttpMethod.POST,"/users/**").hasRole("USER")
                .antMatchers(HttpMethod.GET,"/users/**").hasRole("USER")
                .antMatchers(HttpMethod.GET,"/users/**").hasRole("USER")
                .antMatchers("/gifts/**").hasRole("user")
                .anyRequest().authenticated();


    }


    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/v2/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**");
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
