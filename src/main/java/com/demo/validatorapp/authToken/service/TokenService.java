package com.demo.validatorapp.authToken.service;

import com.demo.validatorapp.authToken.controller.dto.TokenResponseDTO;

public interface TokenService {

    TokenResponseDTO generateBearerTokenByUser(String username);

}
