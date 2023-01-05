package com.ppmtool.ppmtool.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ControllerUtils {

    public Map<String, List<String>> getInvalidObjectErrors(BindingResult result) {
        Map<String, List<String>> res = new HashMap<>();
        if (result.hasErrors()) {
            for (FieldError error : result.getFieldErrors()) {
                String field = error.getField();
                if (!res.containsKey(field)) {
                    res.put(field, new ArrayList<>());
                }
                res.get(field).add(error.getDefaultMessage());
            }
        }
        return res;
    }

    @RestController
    @ControllerAdvice
    public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

        @ExceptionHandler
        public final ResponseEntity<Object> handleException(RuntimeException ex, WebRequest req) {
            return new ResponseEntity(new ExceptionResponse(ex.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    public class ExceptionResponse {
        private String message;

        public ExceptionResponse(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
