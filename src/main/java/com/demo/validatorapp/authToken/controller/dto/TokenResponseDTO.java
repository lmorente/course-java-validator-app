package com.demo.validatorapp.authToken.controller.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenResponseDTO {

    private String type;

    private String value;
}
