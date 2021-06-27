package com.demo.validatorapp.validator.service.impl;


import com.demo.validatorapp.validator.controller.dto.IdentificationDocumentDTO;
import com.demo.validatorapp.validator.service.IdentificationValidatorService;
import com.demo.validatorapp.validator.service.exceptions.FormatException;
import com.demo.validatorapp.validator.service.exceptions.TypeException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Objects;

@Service
public class IdentificationValidatorServiceImpl implements IdentificationValidatorService {

    private static final String[] letterID = {"T", "R", "W", "A", "G", "M", "Y", "F", "P", "D", "X", "B", "N", "J", "Z", "S", "Q", "V", "H", "L", "C", "K", "E"};
    private static final String[] letterCIF = {"J", "A", "B", "C", "D", "E", "F", "G", "H", "I"};
    private static final String firstLetterCIF ="NPQRSW";
    private static final Integer NINE = 9;
    private static final String PAIR = "pair";
    private static final String ODD = "odd";
    private static final String X = "X";
    private static final String Y = "Y";
    private static final String Z = "Z";
    private static final String NIF = "nif";
    private static final String NIE = "nie";
    private static final String CIF = "cif";

    @Override
    public Boolean validate(IdentificationDocumentDTO identification) throws FormatException, TypeException {
        Boolean correct = Boolean.FALSE;
        if (Objects.nonNull(identification) && !ObjectUtils.isEmpty(identification.getType())) {
            switch (identification.getType().getDocument()) {
                case NIF:
                    correct = checkNIF(identification.getValue());
                    break;
                case NIE:
                    correct = checkNIE(identification.getValue());
                    break;
                case CIF:
                    correct = checkCIF(identification.getValue());
            }
            return correct;
        }
        throw new TypeException("Type of identification document not found");
    }

    private Boolean checkNIF(String value) throws FormatException {
        Boolean correct = Boolean.FALSE;
        if(!ObjectUtils.isEmpty(value)){
            String controlLetter = getLetter(value);
            String numberText = value.substring(0, value.length() - 1);
            correct = checkLetter(numberText, controlLetter);
        }
        return correct;
    }

    private Boolean checkNIE(String value) throws FormatException {
        Boolean correct = Boolean.FALSE;
        String letterASCII;
        String formatInitValue = null;
        if (!ObjectUtils.isEmpty(value)) {
            String controlLetter = getLetter(value);
            if (Character.isLetter(value.charAt(0))) {
                letterASCII = String.valueOf(value.charAt(0)).toUpperCase();
                switch (letterASCII) {
                    case X:
                        formatInitValue = 0 + value.substring(1, value.length() - 1);
                        break;
                    case Y:
                        formatInitValue = 1 + value.substring(1, value.length() - 1);
                        break;
                    case Z:
                        formatInitValue = 2 + value.substring(1, value.length() - 1);
                }
                if (Objects.nonNull(formatInitValue)) {
                    String numberText = formatInitValue.substring(0, value.length() - 1);
                    correct = checkLetter(numberText, controlLetter);
                }
                return correct;
            }
        }
        throw new FormatException("Wrong document format");
    }

    private Boolean checkCIF(String value) throws FormatException {
        Boolean correct = Boolean.FALSE;
        if(!ObjectUtils.isEmpty(value) && NINE.equals(value.length()) && Character.isLetter(value.charAt(0)) ){
            String letter = value.substring(0, 1);
            String number = value.substring(1, (value.length() - 1));
            String ControlDigit = value.substring(value.length() - 1);

            Integer controlNumber = 0;
            try {
                Integer.valueOf(number);
                Integer sumPairs = sumCIF(number, PAIR);
                Integer sumOddNumber = sumCIF(number, ODD);
                Integer unit = (sumPairs + sumOddNumber) % 10;
                if(unit > 0){
                    controlNumber = 10 - unit;
                }
                if(value.startsWith("00") | (Character.isLetter(letter.charAt(0)) && firstLetterCIF.contains(letter.toUpperCase()))){
                    if(letterCIF[controlNumber].equals(ControlDigit)){
                        correct = Boolean.TRUE;
                    }
                } else {
                    if(controlNumber.toString().equals(ControlDigit)){
                        correct = Boolean.TRUE;
                    }
                }
            } catch (NumberFormatException e) {
                throw new FormatException("Wrong document format");
            }
            return correct;
        }
        throw new FormatException("Wrong document format");
    }


    private Integer sumCIF(String number, String type) {
        Integer sum = 0;
        if(PAIR.equals(type)){
            for(int index = 1; index < number.length(); index += 2){
                sum += Integer.valueOf(String.valueOf(number.charAt(index)));
            }
        } else if(ODD.equals(type)){
            for(int index = 0; index < number.length(); index += 2){
                Integer result = 2 *  Integer.valueOf(String.valueOf(number.charAt(index)));
                if(result.toString().length() > 1){
                    String sumText = result.toString();
                    result = Integer.valueOf(sumText.substring(0,1)) + Integer.valueOf(sumText.substring(1));
                }
                sum += result;
            }
        }
        return sum;
    }

    private String getLetter(String value) throws FormatException {
        String letter;
        if(Character.isLetter(value.charAt(value.length() - 1))){
            letter = value.substring(value.length() - 1).toUpperCase();
        } else {
            throw new FormatException("Wrong document format");
        }
        return letter;
    }

    private Boolean checkLetter(String numberText, String letter) throws FormatException {
        Boolean evaluation = Boolean.FALSE;
        try {
            Integer numbers = Integer.valueOf(numberText);
            Integer module = numbers % 23;
            if(letterID[module].equals(letter)){
                evaluation = Boolean.TRUE;
            }
        } catch (NumberFormatException e) {
            throw new FormatException("Wrong document format");
        }
        return evaluation;
    }
}
