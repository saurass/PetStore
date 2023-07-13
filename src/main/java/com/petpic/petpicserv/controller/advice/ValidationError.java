package com.petpic.petpicserv.controller.advice;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class ValidationError {
    String field;
    String error;
}
