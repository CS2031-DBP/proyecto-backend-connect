package com.example.Connect.Security;

import com.example.Connect.Usuario.Application.CustomUserDetailsService;
import com.example.Connect.Exception.CustomException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService customUserDetailsService;
    private static final List<String> EXCLUDE_URLS = Arrays.asList(
      "/auth/login",
      "/auth/register",
      "/ws"
    );

    public JwtAuthenticationFilter(JwtUtil jwtUtil, CustomUserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.customUserDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {


        final String servletPath = request.getServletPath();
        if (EXCLUDE_URLS.stream().anyMatch(servletPath::startsWith)) {
            chain.doFilter(request, response);
            return;
        }

        final String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            handleException(response, 401, "Authorization header missing or invalid");
            return;
        }

        String username = null;
        String jwt = null;

        try {
            jwt = authorizationHeader.substring(7);
            username = jwtUtil.extractUsername(jwt);
            String role = jwtUtil.extractRole(jwt);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                Long userId = Long.parseLong(username);
                logger.info("User ID: " + userId);
                logger.info("Role: " + role);
                UserDetails userDetails = this.customUserDetailsService.loadUserById(userId);

                if (jwtUtil.validateToken(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken
                            .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                } else {
                    throw new CustomException(403, "Forbidden: Invalid token");
                }
            }

            chain.doFilter(request, response);
        } catch (ExpiredJwtException ex) {
            handleException(response, 401, "Token expired");
        } catch (SignatureException ex) {
            handleException(response, 401, "Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            handleException(response, 401, "Invalid JWT token");
        } catch (UnsupportedJwtException ex) {
            handleException(response, 401, "JWT token is unsupported");
        } catch (IllegalArgumentException ex) {
            handleException(response, 401, "JWT claims string is empty");
        } catch (CustomException ex) {
            handleException(response, ex.getCode(), ex.getMessage());
        }
    }

    private void handleException(HttpServletResponse response, int statusCode, String message) throws IOException {
        response.setStatus(statusCode);
        response.setContentType("application/json");
        response.getWriter().write("{\"status\": false, \"code\": " + statusCode + ", \"message\": \"" + message + "\"}");
    }
}
