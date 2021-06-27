package com.demo.validatorapp.validator.service;


import com.demo.validatorapp.validator.controller.dto.IdentificationDocumentDTO;
import com.demo.validatorapp.validator.service.exceptions.FormatException;
import com.demo.validatorapp.validator.service.exceptions.TypeException;

public interface IdentificationValidatorService {

    Boolean validate(IdentificationDocumentDTO identification) throws FormatException, TypeException;
}
