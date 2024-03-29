package com.demo.validatorapp.validator.controller.dto;


import com.demo.validatorapp.validator.service.enums.IdentificationTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IdentificationDocumentDTO {

    private IdentificationTypeEnum type;

    private String value;
}
