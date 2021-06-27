package com.demo.validatorapp.validator.controller;

import com.demo.validatorapp.validator.controller.dto.AmountDTO;
import com.demo.validatorapp.validator.controller.dto.IdentificationDocumentDTO;
import com.demo.validatorapp.validator.controller.dto.odto.ErrorResponse;
import com.demo.validatorapp.validator.controller.dto.odto.ValidationResponse;
import com.demo.validatorapp.validator.service.AmountValidatorService;
import com.demo.validatorapp.validator.service.IdentificationValidatorService;
import com.demo.validatorapp.validator.service.UserDataValidatorService;
import com.demo.validatorapp.validator.service.exceptions.FormatException;
import com.demo.validatorapp.validator.service.exceptions.TypeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
public class ValidatorController {

    @Autowired
    private IdentificationValidatorService identificationValidatorService;

    @Autowired
    private UserDataValidatorService userDataValidatorService;

    @Autowired
    private AmountValidatorService amountValidatorService;


    private static final String MESSAGE_EMPTY_FIELDS = "Fill in all the fields";
    private static final String CODE_BAD_REQUEST = "400";


    @RequestMapping(value = "/validator/idocument", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Objects> validateIdentification(@RequestBody IdentificationDocumentDTO identification){
        if(Objects.nonNull(identification) && !ObjectUtils.isEmpty(identification.getType()) && !ObjectUtils.isEmpty(identification.getValue())) {
            try {
                Boolean validation = identificationValidatorService.validate(identification);
                return new ResponseEntity(new ValidationResponse(validation), HttpStatus.ACCEPTED);
            } catch (FormatException | TypeException e) {
                return new ResponseEntity(new ErrorResponse(CODE_BAD_REQUEST, e.getMessage()), HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity(new ErrorResponse(CODE_BAD_REQUEST, MESSAGE_EMPTY_FIELDS), HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/validator/name", method = RequestMethod.POST)
    public ResponseEntity<Objects> validateName(@RequestBody String name){
        if(Objects.nonNull(name) && !ObjectUtils.isEmpty(name)) {
            Boolean validation = userDataValidatorService.isName(name);
            return new ResponseEntity(new ValidationResponse(validation), HttpStatus.ACCEPTED);
        }
        return new ResponseEntity(new ErrorResponse(CODE_BAD_REQUEST, MESSAGE_EMPTY_FIELDS), HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/validator/mail", method = RequestMethod.POST)
    public ResponseEntity<Objects> validateMail(@RequestBody String mail){
        if(Objects.nonNull(mail) && !ObjectUtils.isEmpty(mail)) {
            Boolean validation = userDataValidatorService.isAddressMail(mail);
            return new ResponseEntity(new ValidationResponse(validation), HttpStatus.ACCEPTED);
        }
        return new ResponseEntity(new ErrorResponse(CODE_BAD_REQUEST, MESSAGE_EMPTY_FIELDS), HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/validator/amount", method = RequestMethod.POST)
    public ResponseEntity<Objects> validateMail(@RequestBody AmountDTO amount) {
        if(Objects.nonNull(amount) && !ObjectUtils.isEmpty(amount.getType()) && !ObjectUtils.isEmpty(amount.getValue())) {
            try {
                Boolean validation = amountValidatorService.validate(amount);
                return new ResponseEntity(new ValidationResponse(validation), HttpStatus.ACCEPTED);
            } catch (TypeException e){
                return new ResponseEntity(new ErrorResponse(CODE_BAD_REQUEST, e.getMessage()), HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity(new ErrorResponse(CODE_BAD_REQUEST, MESSAGE_EMPTY_FIELDS), HttpStatus.BAD_REQUEST);
    }

}
