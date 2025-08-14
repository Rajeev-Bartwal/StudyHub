package com.rajeev.StudyHub.Filter;

import com.rajeev.StudyHub.Security.CustomUserDetailService;
import com.rajeev.StudyHub.Security.JwtService;
//import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public  class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    ApplicationContext context;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        //Only work with request
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;

        //Get token....
        if(authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            try {
                username = jwtService.getUserName(token);
            }catch(IllegalArgumentException e)
            {
                System.out.println(e + "jwt token is not valid");
            } catch(ExpiredJwtException e){
                System.out.println("Token is expired..");
            }
        }


//        //Authenticate or Validate the Token
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            System.out.println("JwtAuthFilter triggered for: " + request.getServletPath());
            UserDetails userDetails = context.getBean(CustomUserDetailService.class).loadUserByUsername(username);
            System.out.println("Loaded user: " + userDetails);

            if (jwtService.validateToken(token, userDetails)) {
                System.out.println("Token is valid, setting authentication");
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            } else {
                System.out.println("Token validation failed");
            }
        }


        filterChain.doFilter(request,response);
    }
}
