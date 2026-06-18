package com.example.UserCRUD.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final jwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        // Step 1 — read the Authorization header
        // Expected format: "Bearer eyJhbGciOiJIUzI1NiJ9..."
        final String authHeader = request.getHeader("Authorization");

        // Step 2 — if no header, or it doesn't start with "Bearer ", skip JWT logic entirely
        // Let the request continue — SecurityConfig will decide if it's allowed (e.g. /login)
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Step 3 — extract just the token part (remove "Bearer " prefix)
        final String token = authHeader.substring(7);
        final String email = jwtUtil.extractEmail(token);

        // Step 4 — only proceed if email was extracted AND no one is authenticated yet in this request
        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = userDetailsService.loadUserByUsername(email);

            // Step 5 — validate signature + expiry
            if (jwtUtil.validateToken(token, userDetails.getUsername())) {

                // Step 6 — build an "Authentication" object Spring Security understands
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null, // no credentials needed — already proven via JWT
                        userDetails.getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Step 7 — register this authentication for the CURRENT request only
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // Step 8 — always continue the chain, whether authenticated or not
        // SecurityConfig will block it later if it required authentication and none was set
        filterChain.doFilter(request, response);
    }
}