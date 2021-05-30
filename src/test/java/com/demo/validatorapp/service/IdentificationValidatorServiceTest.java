package com.demo.validatorapp.service;


import com.demo.validatorapp.controller.dto.IdentificationDocumentDTO;
import com.demo.validatorapp.service.enums.IdentificationTypeEnum;
import com.demo.validatorapp.service.exceptions.FormatException;
import com.demo.validatorapp.service.exceptions.TypeException;
import com.demo.validatorapp.service.impl.IdentificationValidatorServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class IdentificationValidatorServiceTest {

    @InjectMocks
    IdentificationValidatorServiceImpl identificationValidatorService;

    @Test(expected = TypeException.class)
    public void wrongType() throws TypeException, FormatException {
        IdentificationDocumentDTO nif = IdentificationDocumentDTO.builder().value("0733.584T").build();
        identificationValidatorService.validate(nif);
    }

    @Test
    public void correctNIF() {
        List<String> nifs = Arrays.asList("07330584r", "46139404R", "23199768j", "07218681Q");
        nifs.forEach( nif -> {
            IdentificationDocumentDTO evaluateNif = IdentificationDocumentDTO.builder().type(IdentificationTypeEnum.NIF).value(nif).build();
            Boolean result = null;
            try {
                result = identificationValidatorService.validate(evaluateNif);
            } catch (FormatException | TypeException e) { }
            Assert.assertEquals(result, Boolean.TRUE);
        });
    }

    @Test
    public void correctNIE() {
        List<String> nies = Arrays.asList("Z6004274z", "X0341198Q", "Z9607117E", "Y8352797Q", "x4807183e", "X9508414F");
        nies.forEach( nie -> {
            IdentificationDocumentDTO evaluateNie = IdentificationDocumentDTO.builder().type(IdentificationTypeEnum.NIE).value(nie).build();
            Boolean result = null;
            try {
                result = identificationValidatorService.validate(evaluateNie);
            } catch (FormatException | TypeException e) { }
            Assert.assertEquals(result, Boolean.TRUE);
        });
    }

    @Test
    public void correctCIF() {
        List<String> cifs = Arrays.asList("v11720679", "D25566159", "Q1370672F", "N7205883G", "Q1865218J");
        cifs.forEach( cif -> {
            IdentificationDocumentDTO evaluateCif = IdentificationDocumentDTO.builder().type(IdentificationTypeEnum.CIF).value(cif).build();
            Boolean result = null;
            try {
                result = identificationValidatorService.validate(evaluateCif);
            } catch (FormatException | TypeException e) { }
            Assert.assertEquals(result, Boolean.TRUE);
        });
    }

    @Test
    public void incorrectNIF() {
        List<String> nifs = Arrays.asList("07330584t", "46139404e", "23199768L", "07218681Z");
        nifs.forEach( nif -> {
            IdentificationDocumentDTO evaluateCif = IdentificationDocumentDTO.builder().type(IdentificationTypeEnum.NIF).value(nif).build();
            Boolean result = null;
            try {
                result = identificationValidatorService.validate(evaluateCif);
            } catch (FormatException | TypeException e) { }
            Assert.assertEquals(result, Boolean.FALSE);
        });
    }

    @Test
    public void incorrectNIE() {
        List<String> nies = Arrays.asList("X6004274z", "X0341198G", "Z9607117S", "Y8352797K", "x4807183l", "Y9508414F");
        nies.forEach( nie -> {
            IdentificationDocumentDTO evaluateNie = IdentificationDocumentDTO.builder().type(IdentificationTypeEnum.NIE).value(nie).build();
            Boolean result = null;
            try {
                result = identificationValidatorService.validate(evaluateNie);
            } catch (FormatException | TypeException e) { }
            Assert.assertEquals(result, Boolean.FALSE);
        });
    }

    @Test
    public void incorrectCIF() {
        List<String> cifs = Arrays.asList("P2556615T", "L1370672f", "Y72058834", "J18652186");
        cifs.forEach( cif -> {
            IdentificationDocumentDTO evaluateCif = IdentificationDocumentDTO.builder().type(IdentificationTypeEnum.CIF).value(cif).build();
            Boolean result = null;
            try {
                result = identificationValidatorService.validate(evaluateCif);
            } catch (FormatException | TypeException e) { }
            Assert.assertEquals(result, Boolean.FALSE);
        });
    }

    @Test(expected = FormatException.class)
    public void incorrectNumberFormatNIF() throws FormatException, TypeException {
        IdentificationDocumentDTO nif = IdentificationDocumentDTO.builder().type(IdentificationTypeEnum.NIF).value("0733.584T").build();
        identificationValidatorService.validate(nif);
    }

    @Test(expected = FormatException.class)
    public void incorrectNumberFormatNIE() throws FormatException, TypeException {
        IdentificationDocumentDTO nie = IdentificationDocumentDTO.builder().type(IdentificationTypeEnum.NIE).value("981k1786j").build();
        identificationValidatorService.validate(nie);
    }

    @Test(expected = FormatException.class)
    public void incorrectNumberFormatCIF() throws FormatException, TypeException {
        IdentificationDocumentDTO cif = IdentificationDocumentDTO.builder().type(IdentificationTypeEnum.CIF).value("A87,4984e").build();
        identificationValidatorService.validate(cif);
    }

    @Test(expected = FormatException.class)
    public void incorrectLetterFormatNIF() throws FormatException, TypeException {
        IdentificationDocumentDTO nif = IdentificationDocumentDTO.builder().type(IdentificationTypeEnum.NIF).value("073375849").build();
        identificationValidatorService.validate(nif);
    }

    @Test(expected = FormatException.class)
    public void incorrectLetterFormatNIE() throws FormatException, TypeException {
        IdentificationDocumentDTO nie = IdentificationDocumentDTO.builder().type(IdentificationTypeEnum.NIE).value("198115786j").build();
        identificationValidatorService.validate(nie);
    }

    @Test(expected = FormatException.class)
    public void incorrectLetterFormatCIF() throws FormatException, TypeException {
        IdentificationDocumentDTO cif = IdentificationDocumentDTO.builder().type(IdentificationTypeEnum.CIF).value("A8749840").build();
        identificationValidatorService.validate(cif);
    }

    @Test(expected = FormatException.class)
    public void incorrectLenghtCIF() throws FormatException, TypeException {
        IdentificationDocumentDTO cif = IdentificationDocumentDTO.builder().type(IdentificationTypeEnum.CIF).value("A874984870").build();
        identificationValidatorService.validate(cif);
    }
}
