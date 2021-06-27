package com.demo.validatorapp.authToken.service.impl;

import com.demo.validatorapp.authToken.controller.dto.TokenResponseDTO;
import com.demo.validatorapp.authToken.service.TokenService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TokenServiceImpl implements TokenService {

    @Value(value="${token.rol}")
    private String rol;

    @Value(value="${token.secretKey}")
    private String secret;

    @Value(value="${token.expiration}")
    private Integer expiration;

    private static String AUTHORITIES = "authorities";
    private static String BEARER = "Bearer";

    @Override
    public TokenResponseDTO generateBearerTokenByUser(String username) {

        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList(rol);

        String token = Jwts.builder()
                .setSubject(username)
                .claim(AUTHORITIES, grantedAuthorities.stream()
                                    .map(GrantedAuthority::getAuthority)
                                    .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date((System.currentTimeMillis() + expiration)))
                .signWith(SignatureAlgorithm.HS512, DatatypeConverter.parseBase64Binary(secret))
                .compact();

        TokenResponseDTO tokenDTO = TokenResponseDTO.builder()
                .type(BEARER)
                .value(token)
                .build();
        return tokenDTO;
    }
}
