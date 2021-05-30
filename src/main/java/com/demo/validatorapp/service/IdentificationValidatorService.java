package com.demo.validatorapp.service;


import com.demo.validatorapp.controller.dto.IdentificationDocumentDTO;
import com.demo.validatorapp.service.exceptions.FormatException;
import com.demo.validatorapp.service.exceptions.TypeException;

public interface IdentificationValidatorService {

    Boolean validate(IdentificationDocumentDTO identification) throws FormatException, TypeException;
}
