package com.demo.validatorapp.validator.service;


import com.demo.validatorapp.validator.controller.dto.AmountDTO;
import com.demo.validatorapp.validator.service.exceptions.TypeException;

public interface AmountValidatorService {

    Boolean validate(AmountDTO amount) throws TypeException;
}
