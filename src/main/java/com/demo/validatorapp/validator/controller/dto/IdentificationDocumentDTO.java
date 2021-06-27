package com.demo.validatorapp.controller.dto;


import com.demo.validatorapp.service.enums.IdentificationTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IdentificationDocumentDTO {

    IdentificationTypeEnum type;

    String value;
}
