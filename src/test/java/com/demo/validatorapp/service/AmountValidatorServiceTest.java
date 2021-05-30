package com.demo.validatorapp.service;

import com.demo.validatorapp.controller.dto.AmountDTO;
import com.demo.validatorapp.service.exceptions.TypeException;
import com.demo.validatorapp.service.impl.AmountValidatorServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;

@RunWith(MockitoJUnitRunner.class)
public class AmountValidatorServiceTest {

    @InjectMocks
    AmountValidatorServiceImpl amountValidatorService;

    @Test
    public void correctAmount() throws TypeException {
        AmountDTO amount = AmountDTO.builder().type("EUR").value(new BigDecimal("3453.27")).build();
        Boolean result = amountValidatorService.validate(amount);
        Assert.assertEquals(result, Boolean.TRUE);
    }

    @Test
    public void incorrectAmount() throws TypeException {
        AmountDTO amount = AmountDTO.builder().type("EUR").value(new BigDecimal("-3453.27")).build();
        Boolean result = amountValidatorService.validate(amount);
        Assert.assertEquals(result, Boolean.FALSE);
    }

    @Test(expected = TypeException.class)
    public void incorrectCurrency() throws TypeException {
        AmountDTO amount = AmountDTO.builder().type("EURX").value(new BigDecimal("-3453.27")).build();
        amountValidatorService.validate(amount);
    }

}
