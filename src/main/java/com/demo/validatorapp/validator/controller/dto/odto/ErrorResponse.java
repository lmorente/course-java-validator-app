package com.demo.validatorapp.validator.controller.dto.odto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponse {

    private String type;

    private String error;
}
