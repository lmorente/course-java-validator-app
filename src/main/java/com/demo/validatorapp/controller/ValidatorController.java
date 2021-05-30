package com.demo.validatorapp.controller;

import com.demo.validatorapp.controller.dto.AmountDTO;
import com.demo.validatorapp.controller.dto.IdentificationDocumentDTO;
import com.demo.validatorapp.service.AmountValidatorService;
import com.demo.validatorapp.service.IdentificationValidatorService;
import com.demo.validatorapp.service.UserDataValidatorService;
import com.demo.validatorapp.service.exceptions.FormatException;
import com.demo.validatorapp.service.exceptions.TypeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
public class ValidatorController {

    private static final String MESSAGE_WRONG = "Incorrecto";
    private static final String MESSAGE_CORRECT = "Correcto";
    private static final String MESSAGE_EMPTY_FIELDS = "Complete los campos";
    private static final String MESSAGE_WRONG_FORMAT = "Formato incorrecto";
    private static final String MESSAGE_NOT_FOUND = "Tipo no encontrado";

    @Autowired
    IdentificationValidatorService identificationValidatorService;

    @Autowired
    UserDataValidatorService userDataValidatorService;

    @Autowired
    AmountValidatorService amountValidatorService;

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String get(){
        return "Hello world!!";
    }

    @RequestMapping(value = "/validator/id", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String validateIdentification(@RequestBody IdentificationDocumentDTO identification){
        String response = MESSAGE_EMPTY_FIELDS;
        if(Objects.nonNull(identification) && !ObjectUtils.isEmpty(identification.getType()) && !ObjectUtils.isEmpty(identification.getValue())) {
            try {
                Boolean validation = identificationValidatorService.validate(identification);
                response = MESSAGE_WRONG;
                if (validation) {
                    response = MESSAGE_CORRECT;
                }
            } catch (FormatException | TypeException e) {
                response = MESSAGE_WRONG_FORMAT;
            }
        }
        return response;
    }

    @RequestMapping(value = "/validator/name", method = RequestMethod.POST)
    public String validateName(@RequestBody String name){
        String response = MESSAGE_EMPTY_FIELDS;
        if(Objects.nonNull(name) && !ObjectUtils.isEmpty(name)) {
            Boolean validation = userDataValidatorService.isName(name);
            if (validation) {
                response = MESSAGE_CORRECT;
            } else {
                response = MESSAGE_WRONG_FORMAT;
            }
        }
        return response;
    }

    @RequestMapping(value = "/validator/mail", method = RequestMethod.POST)
    public String validateMail(@RequestBody String mail){
        String response = MESSAGE_WRONG_FORMAT;
        if(Objects.nonNull(mail) && !ObjectUtils.isEmpty(mail)) {
            Boolean validation = userDataValidatorService.isAddressMail(mail);
            if (validation) {
                response = MESSAGE_CORRECT;
            } else {
                response = MESSAGE_WRONG_FORMAT;
            }
        }
        return response;
    }

    @RequestMapping(value = "/validator/amount", method = RequestMethod.POST)
    public String validateMail(@RequestBody AmountDTO amount) {
        String response = MESSAGE_EMPTY_FIELDS;
        if(Objects.nonNull(amount) && !ObjectUtils.isEmpty(amount.getType()) && !ObjectUtils.isEmpty(amount.getValue())) {
            try {
                Boolean validation = amountValidatorService.validate(amount);
                if (validation) {
                    response = MESSAGE_CORRECT;
                }else {
                    response = MESSAGE_WRONG_FORMAT;
                }
            } catch (TypeException e){
                response = MESSAGE_NOT_FOUND;
            }
        }
        return response;
    }

}
