package com.springbootangular.springbootjwtangular.config;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private UserDetailsServiceImpl userDetailsServiceImpl;

    private JwtUtils jwtUtils;

    



    public JwtAuthenticationFilter(UserDetailsServiceImpl userDetailsServiceImpl, JwtUtils jwtUtils) {
        this.userDetailsServiceImpl = userDetailsServiceImpl;
        this.jwtUtils = jwtUtils;
    }





    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

                final String requestTokenHeader = request.getHeader("Authorization");
                System.out.println(requestTokenHeader);
                String userName=null;
                String jwtToken=null;

                if(requestTokenHeader!=null && requestTokenHeader.startsWith("Bearer ")){
                    System.out.println("inside");

                    jwtToken=requestTokenHeader.substring(7);
                    try{
                    userName=this.jwtUtils.extractUsername(jwtToken);
                    }catch(ExpiredJwtException e){
                        e.printStackTrace();
                        System.out.println("jwt token expired");

                    }catch(Exception e){
                        e.printStackTrace();
                        System.out.println("error");
                    }


                }else{
                    System.out.println("Invalid token, not starting with Bearer");

                }

                if(userName!=null && SecurityContextHolder.getContext().getAuthentication()==null){
                    final UserDetails userDetails=this.userDetailsServiceImpl.loadUserByUsername(userName);

                    if(this.jwtUtils.validateToken(jwtToken, userDetails)){
                        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken(userDetails, null,userDetails.getAuthorities());
                        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));


                        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                    }

                }else{
                    System.out.println("Token is not valid");
                }

                filterChain.doFilter(request, response);
                
        
    }
    
}
