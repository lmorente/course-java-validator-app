package com.demo.validatorapp.security.utils;

import lombok.Data;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class TokenConfiguration {

    @Value(value="${token.rol}")
    private String rol;

    @Value(value="${token.secretkey}")
    private String secretKey;

    @Value(value="${token.expiration}")
    private String expiration;

}
