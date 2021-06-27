package com.demo.validatorapp.security;

import com.demo.validatorapp.security.utils.JwtFilter;
import com.demo.validatorapp.security.utils.TokenConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    TokenConfiguration tokenConfiguration;

    private static final String AUTH_URL = "/auth";

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.csrf().disable().authorizeRequests()
                .antMatchers(HttpMethod.POST, AUTH_URL).permitAll()
                .anyRequest().authenticated().and()
                .addFilterBefore(new JwtFilter(tokenConfiguration), UsernamePasswordAuthenticationFilter.class);
    }
}
