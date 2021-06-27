package com.demo.validatorapp.service;


import com.demo.validatorapp.controller.dto.AmountDTO;
import com.demo.validatorapp.service.exceptions.TypeException;

public interface AmountValidatorService {

    Boolean validate(AmountDTO amount) throws TypeException;
}
