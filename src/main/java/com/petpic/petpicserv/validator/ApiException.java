package com.petpic.petpicserv.validator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public class ApiException {
    private String message;
    private HttpStatus httpStatus;
}
