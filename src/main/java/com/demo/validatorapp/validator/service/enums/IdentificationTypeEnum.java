package com.demo.validatorapp.service.enums;

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
