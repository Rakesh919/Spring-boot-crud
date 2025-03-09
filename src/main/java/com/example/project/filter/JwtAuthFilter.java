package com.example.project.filter;

import com.example.project.dto.response.ErrorResponse;
import com.example.project.utils.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {

        String requestURI = request.getRequestURI();
        if (requestURI.equals("/create") || requestURI.startsWith(("/login")) || requestURI.startsWith("/otp")) {
            chain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");


        if (authHeader == null || !authHeader.startsWith("Bearer")) {
            sendJsonErrorResponse(response, new ErrorResponse("BAD-401", "Unauthorized access"));
            return;
        }
        String email;
        try {
            String token = authHeader.substring(7);
            email = jwtUtil.verifyToken(token);

            if (email == null) {
                sendJsonErrorResponse(response, new ErrorResponse("BAD-401", "Invalid Token"));
                return;
            }
            request.setAttribute("email", email);

        } catch (Exception e) {
            sendJsonErrorResponse(response, new ErrorResponse("BAD-401", "Invalid Token"));
            return;
        }

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(email, null, Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(authToken);
        chain.doFilter(request, response);
    }


    private void sendJsonErrorResponse(HttpServletResponse response, ErrorResponse responseDto) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        ObjectMapper objectMapper = new ObjectMapper();
        response.getWriter().write(objectMapper.writeValueAsString(responseDto));
    }
}
