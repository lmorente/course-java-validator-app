package com.demo.validatorapp.security.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Configuration
@AllArgsConstructor
public class JwtFilter extends GenericFilterBean {

    @Autowired
    private  TokenConfiguration token;

    private final String AUTHORITIES = "authorities";
    private final String HEADER = "Authorization";
    private final String PREFIX = "Bearer ";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        try{
            if(containsJwtToken(httpRequest)){
                Claims claims = isValidToken(httpRequest);
                if(Objects.nonNull(claims) && Objects.nonNull(claims.get(AUTHORITIES))){
                    setUpSpringAuthentication(claims);
                } else {
                    SecurityContextHolder.clearContext();
                }
            } else {
                SecurityContextHolder.clearContext();
            }
            chain.doFilter(request, response);
        }catch (IOException | ServletException e){
            ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_FORBIDDEN);
            ((HttpServletResponse) response).sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
        }
    }

    /**
     * Method for authentication within Spring flow
     * @param claims
     */
    private void setUpSpringAuthentication(Claims claims) {
        List<String> authorities = (List) claims.get(AUTHORITIES);
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(claims.getSubject(),
                null, authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    private Claims isValidToken(HttpServletRequest httpRequest) {
        String jwt = httpRequest.getHeader(HEADER).replace(PREFIX, "");
        return Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(token.getSecretKey())).parseClaimsJws(jwt).getBody();
    }

    private boolean containsJwtToken(HttpServletRequest httpRequest) {
        String authenticationHeader = httpRequest.getHeader(HEADER);
        return Objects.isNull(authenticationHeader) || !authenticationHeader.startsWith(PREFIX)
                ? Boolean.FALSE : Boolean.TRUE;
    }
}
