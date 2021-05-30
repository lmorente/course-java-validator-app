package com.demo.validatorapp.service.impl;


import com.demo.validatorapp.controller.dto.AmountDTO;
import com.demo.validatorapp.service.AmountValidatorService;
import com.demo.validatorapp.service.dto.CurrencyDictionary;
import com.demo.validatorapp.service.exceptions.TypeException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Objects;


@Service
public class AmountValidatorServiceImpl implements AmountValidatorService {

    @Override
    public Boolean validate(AmountDTO amount) throws TypeException {
        CurrencyDictionary currency = new CurrencyDictionary();
        Map<String, CurrencyDictionary> dictionary = currency.readData();

        if (ObjectUtils.isEmpty(amount.getType()) || Objects.isNull(dictionary.get(amount.getType()))){
                throw new TypeException("Currency not found");
        }
        if (ObjectUtils.isEmpty(amount.getValue()) || amount.getValue().compareTo(BigDecimal.ZERO) < 0){
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }
}
