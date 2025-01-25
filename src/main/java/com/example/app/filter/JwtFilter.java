package com.example.app.filter;

import com.example.app.service.club.ClubDetailsService;
import com.example.app.service.jwt.JWTService;
import com.example.app.service.user.AppUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JWTService jwtService;
    private final ApplicationContext context;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("Inside doFilterInternal method : JwtFilter");
        String requestPath = request.getServletPath();

        if (
                requestPath.equals("/api/v2/users/login") ||
                requestPath.equals("/api/v2/users/register") ||
                requestPath.equals("/api/v2/clubs/register") ||
                requestPath.equals("/api/v2/clubs/login")
        ) {
            System.out.println("Public endpoint accessed: " + requestPath);
            filterChain.doFilter(request, response);
            return;
        }

        String authorization = request.getHeader("Authorization");
        String token = "";
        String username = "";
        String type = "";

        if (authorization != null && authorization.startsWith("Bearer ")) {
            System.out.println("Inside doFilterInternal method : JwtFilter. authorization == null");
            token = authorization.substring(7);
            username = jwtService.extractUsernameFromToken(token);
            type = jwtService.extractClaimFromToken(token, "type"); // Extract the "type" claim (user/club)
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = "user".equals(type) ?
                    context.getBean(AppUserDetailsService.class).loadUserByUsername(username) :
                    context.getBean(ClubDetailsService.class).loadUserByUsername(username);

            if (jwtService.validateToken(token, userDetails)) {
                System.out.println("Inside doFilterInternal method : JwtFilter jwtService.validateToken");
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }


        }

        filterChain.doFilter(request, response);
    }
}
