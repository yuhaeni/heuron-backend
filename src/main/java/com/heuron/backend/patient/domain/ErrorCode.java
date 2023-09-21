package com.heuron.backend.patient.domain;

import lombok.Getter;

@Getter
public enum ErrorCode {

    PATIENT_NOT_FOUND(404, "PATIENT-001", "환자 정보를 찾을 수 없습니다.")
    ,IMG_NOT_FOUND(404, "PATIENT-001", "이미지를 찾을 수 없습니다."),
    ;

    private final int status;
    private final String code;
    private final String description;

    ErrorCode(int status, String code, String description) {
        this.status = status;
        this.code = code;
        this.description = description;
    }
}
