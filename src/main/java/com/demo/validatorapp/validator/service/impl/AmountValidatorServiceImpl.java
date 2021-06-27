package com.demo.validatorapp.service.impl;


import com.demo.validatorapp.controller.dto.AmountDTO;
import com.demo.validatorapp.service.AmountValidatorService;
import com.demo.validatorapp.service.enums.CurrencyEnum;
import com.demo.validatorapp.service.exceptions.TypeException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;



@Service
public class AmountValidatorServiceImpl implements AmountValidatorService {

    @Override
    public Boolean validate(AmountDTO amount) throws TypeException {
        CurrencyEnum currency = CurrencyEnum.valueOf(amount.getType());

        if (ObjectUtils.isEmpty(currency)){
            throw new TypeException("Currency not found");
        } else if (ObjectUtils.isEmpty(amount.getValue()) ||
                amount.getValue().compareTo(BigDecimal.ZERO) < 0){
            return Boolean.FALSE;
        }

        return Boolean.TRUE;
    }
}
