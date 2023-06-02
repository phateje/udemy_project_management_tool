package com.ppmtool.ppmtool.security;

import com.ppmtool.ppmtool.domain.User;
import com.ppmtool.ppmtool.services.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = getJwtFromRequest(request);
            if (jwtTokenProvider.isTokenValid(token)) {
                Long userId = jwtTokenProvider.getUserIdFromToken(token);
                User user = customUserDetailsService.loadUserById(userId);

                // TODO some DRY opportunity here, controller does a lot of this and maybe shouldn't
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                        user, null, Collections.EMPTY_LIST
                );
                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(auth);
            }

        } catch (Exception e) {
            logger.error("couldn't authenticate in security context", e);
        }
        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest req) {
        String token = req.getHeader("Authorization");
        if (!StringUtils.hasText(token)) {
            return null;
        }

        if (token.startsWith("Bearer ")) {
            return token.substring(7);// remove "Bearer "
        }
        return null;
    }
}
