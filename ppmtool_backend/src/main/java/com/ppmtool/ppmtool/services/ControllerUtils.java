package com.ppmtool.ppmtool.services;

import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

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
}
