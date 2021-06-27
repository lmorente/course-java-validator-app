package com.demo.validatorapp.validator.service.enums;

public enum IdentificationTypeEnum {
    NIF("nif"), NIE("nie"), CIF("cif");

    public String document;

    IdentificationTypeEnum(String type) {
        this.document = type;
    }

    public String getDocument() {
        return document;
    }

}
