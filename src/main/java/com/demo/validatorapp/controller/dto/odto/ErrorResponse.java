package com.demo.validatorapp.controller.dto.odto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponse {

    String type;

    String error;
}
