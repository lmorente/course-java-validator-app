package com.demo.validatorapp.authToken.controller;

import com.demo.validatorapp.authToken.controller.dto.TokenResponseDTO;
import com.demo.validatorapp.authToken.service.TokenService;
import com.demo.validatorapp.authToken.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    private static final String ERROR_MESSAGE_403 = "Invalid user credentials";

    @Autowired
    TokenService tokenService;

    @Autowired
    UserService userService;

    @PostMapping("/auth")
    public ResponseEntity<TokenResponseDTO> login(@RequestParam("user") String user, @RequestParam("password") String password){
        if(userService.validateUserGeneric(user, password)){
            return new ResponseEntity<>(tokenService.generateBearerTokenByUser(user), HttpStatus.ACCEPTED);
        }

        TokenResponseDTO errorToken = TokenResponseDTO.builder()
                .type(HttpStatus.FORBIDDEN.toString())
                .value(ERROR_MESSAGE_403)
                .build();
        return new ResponseEntity<>(errorToken, HttpStatus.FORBIDDEN);
    }
}
