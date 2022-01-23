package com.spinnerapp.controller;

import com.spinnerapp.repository.UserRepository;
import com.spinnerapp.security.common.domain.JwtToken;
import com.spinnerapp.security.common.domain.LoginDto;
import com.spinnerapp.security.common.service.JwtAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtAuthService jwtAuthService;
    private final UserRepository userRepository;

    @PostMapping
    public JwtToken authenticate(@RequestBody LoginDto loginDto) {

        Authentication authenticationToken = new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

//        User user = userRepository.findByUsername(loginDto.getUsername());
        String tokenPlain = jwtAuthService.issueToken(authentication, Duration.ofDays(1));

        String pattern = "dd-MM-yyyy hh:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(Date.from(Instant.now().plus(Duration.ofDays(1))));

        return new JwtToken(tokenPlain, date);
    }

}
