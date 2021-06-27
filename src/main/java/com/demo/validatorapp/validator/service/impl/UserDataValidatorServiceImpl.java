package com.demo.validatorapp.validator.service.impl;

import com.demo.validatorapp.validator.service.UserDataValidatorService;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
public class UserDataValidatorServiceImpl implements UserDataValidatorService {

    private static final String  ALLOWED_CHARACTERS = " '´`";

    @Override
    public Boolean isAddressMail(String mail) {
        Boolean result = Boolean.FALSE;
        if(!ObjectUtils.isEmpty(mail)) {
            result = checkMail(mail);
        }
        return result;
    }

    @Override
    public Boolean isName(String name) {
        Boolean result = Boolean.FALSE;
        if(!ObjectUtils.isEmpty(name)){
            Integer index = 0;
            result = Boolean.TRUE;
            while (index <= name.length() - 1){
                if(!Character.isLetter(name.charAt(index)) &&
                        !ALLOWED_CHARACTERS.contains(String.valueOf(name.charAt(index)))){
                    result = Boolean.FALSE;
                    break;
                }
                index += 1 ;
            }
        }
        return result;
    }

    private Boolean checkMail(String mail){
        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher mather = pattern.matcher(mail);
        return mather.find();
    }

}
