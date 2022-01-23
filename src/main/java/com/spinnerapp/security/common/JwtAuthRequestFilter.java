package com.spinnerapp.security.common;

import com.spinnerapp.security.common.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthRequestFilter extends OncePerRequestFilter {

    private final List<AuthService> authServices;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("in filter555555555555555555555");
        log.info("auth:{}", authServices);
        Optional<Authentication> authOptioanal = Optional.empty();
        for (AuthService authService : authServices) {
            authOptioanal = authOptioanal.or(() -> authService.getAuthentication(request));
            authOptioanal.ifPresent(auth -> SecurityContextHolder.getContext().setAuthentication(auth));
        }
        filterChain.doFilter(request, response);
    }
}
