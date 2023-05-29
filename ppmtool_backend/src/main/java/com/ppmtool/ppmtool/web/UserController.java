package com.ppmtool.ppmtool.web;

import com.ppmtool.ppmtool.domain.User;
import com.ppmtool.ppmtool.services.ControllerUtils;
import com.ppmtool.ppmtool.services.UserService;
import com.ppmtool.ppmtool.validator.UserValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private ControllerUtils controllerUtils;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody User user, BindingResult result) {
        // might be better to have this in the service, but it's more convenient to have it here cause of the binding result?
        userValidator.validate(user, result);
        var errors = controllerUtils.getInvalidObjectErrors(result);
        if (!errors.isEmpty()) {
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(userService.saveUser(user), HttpStatus.OK);
    }
}
