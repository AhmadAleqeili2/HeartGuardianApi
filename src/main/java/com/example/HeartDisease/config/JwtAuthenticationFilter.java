package com.example.HeartDisease.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Arrays;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    public static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private final RequestMatcher publicUrls;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    public JwtAuthenticationFilter() {
        this.publicUrls = new OrRequestMatcher(
                Arrays.asList(
                        new AntPathRequestMatcher("/login"),
                        new AntPathRequestMatcher("/register"),
                        new AntPathRequestMatcher("/forgotpassword"),
                        new AntPathRequestMatcher("/resetpassword"),
                        new AntPathRequestMatcher("/resetPasswordRequest"),
                        new AntPathRequestMatcher("/")
                )
        );
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        if (publicUrls.matches(request)) {
            logger.debug("Skipping JWT filter for public URL: {}", request.getServletPath());
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String token = getJwtFromRequest(request);
            if (token != null) {
                if (jwtTokenProvider.validateToken(token)) {
                    String username = jwtTokenProvider.getUsernameFromToken(token);
                    List<GrantedAuthority> authorities = jwtTokenProvider.getAuthorities(token);

                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(username, null, authorities);
                    SecurityContextHolder.getContext().setAuthentication(authentication);

                    logger.debug("Authenticated user: {}", username);
                } else {
                    logger.warn("Invalid JWT token");
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }
            } else {
                logger.debug("No JWT token found in request");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

            filterChain.doFilter(request, response);

        } catch (Exception ex) {
            logger.error("Could not set user authentication in security context", ex);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}