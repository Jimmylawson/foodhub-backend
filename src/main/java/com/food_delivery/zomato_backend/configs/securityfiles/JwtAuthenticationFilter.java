package com.food_delivery.zomato_backend.configs.securityfiles;

import com.food_delivery.zomato_backend.exceptions.ExceptionJwtException;
import com.food_delivery.zomato_backend.service.auth.CustomUserDetailService;
import com.food_delivery.zomato_backend.service.auth.JwtTokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.security.SignatureException;


@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter  extends OncePerRequestFilter {
    private final JwtTokenService jwtTokenService;
    private final CustomUserDetailService customUserDetailService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try{
            /// Get the json token
            String jwt = getJwtFromRequest(request);
            /// Check if the token is exist
            if(jwt != null && SecurityContextHolder.getContext().getAuthentication() == null){
                log.info("JWT token found: {}", jwt);
               /// // get the username from the token's claims
                String username = jwtTokenService.extractUsernameFromJwt(jwt);
                /// Load the full user details from the database using the username
                ///  including user's roles/ authorities, password hashed
                UserDetails userDetails = customUserDetailService.loadUserByUsername(username);
                /// Additional validation to ensure the token matches the user details
                if(jwtTokenService.isTokenValid(jwt,userDetails)){

                    /// Create an Authentication Object
                /// This object represents the authenticated user in Spring Security
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, /// Principal(UserDetails)
                            null, /// Credentials (null since we don't need password)
                            userDetails.getAuthorities()); ///User's roles/authorities

                /// Add additional details to the authentication object
                /// Includes information like IP address, and session ID
                    authentication.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request)
                    );

                    /// Stores the authentication in Spring Security context

                    SecurityContextHolder.getContext().setAuthentication(authentication);

                }
            }

        } catch (ExceptionJwtException ex) {
            log.error("JWT Token has expired: {}", ex.getMessage());
            request.setAttribute("expired", ex.getMessage());
        } catch (io.jsonwebtoken.JwtException ex) {
            log.error("Invalid JWT token: {}", ex.getMessage());
            request.setAttribute("invalid", ex.getMessage());
        } catch (Exception ex) {
            log.error("Could not set user authentication in security context", ex);
        }

        /// Pass the request to the next filter in the filter chain
        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");
        if(bearerToken != null && bearerToken.startsWith("Bearer ")){
            log.info("Bearer token found: {}", bearerToken);
            return bearerToken.substring(7);
        }

        return null;
    }

    /// This method determines which URLS should bypass the JWT authentication filter
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();

        return path.startsWith("/api/v1/auth/") ||
                path.startsWith("/v3/api-docs/") ||
                path.startsWith("/api/v1/public/") ||
                path.startsWith("/swagger-ui/");
    }
}
