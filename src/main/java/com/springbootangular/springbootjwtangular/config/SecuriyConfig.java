package com.springbootangular.springbootjwtangular.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;




@EnableWebSecurity
@Configuration
public class SecuriyConfig {

    private UserDetailsService userDetailsService;

    private CustomCors customCors;

    private JwtAuthenticationEntryPoint unauthorizedHandler;

    private JwtAuthenticationFilter jwtAuthenticationFilter;

    

    
    public SecuriyConfig(UserDetailsService userDetailsService,CustomCors customCors,JwtAuthenticationEntryPoint unauthorizedHandler,
    JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter=jwtAuthenticationFilter;
        this.unauthorizedHandler=unauthorizedHandler;
        this.customCors=customCors;
        this.userDetailsService = userDetailsService;
    }


    @Bean
    public static BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

   

    

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http)throws Exception{

        http.csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .cors(corsCustomizer -> corsCustomizer.configurationSource(customCors))
                                    .authorizeHttpRequests((authorize)->authorize.requestMatchers("/create","/generate-token","/static/**","/uploads/**").permitAll()
                                            .requestMatchers("/dashboard").hasAuthority("ADMIN")
                                            .anyRequest().authenticated())
                                            .exceptionHandling(exception->exception.authenticationEntryPoint(unauthorizedHandler))
                                            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);




        return http.build();

    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }



    @Autowired
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{

        auth.userDetailsService(userDetailsService)
            .passwordEncoder(passwordEncoder());
        

    }
    
}
