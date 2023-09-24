package com.heuron.backend.patient.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDto<T> {
    private boolean result;
    private T data;
    private String msg;
}
