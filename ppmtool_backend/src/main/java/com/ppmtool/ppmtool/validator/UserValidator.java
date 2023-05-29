package com.ppmtool.ppmtool.validator;


import com.ppmtool.ppmtool.domain.User;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;

        if (user.getPassword().length() < 8) {
            errors.rejectValue("password", "length", "password is too short");
        }

        if (user.getPassword().equals(user.getConfirmPassword()) == false) {
            errors.rejectValue("password", "match", "password has to match");
        }

    }
}
