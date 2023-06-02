package com.ppmtool.ppmtool.web;

import com.ppmtool.ppmtool.domain.User;
import com.ppmtool.ppmtool.payload.JwtLoginSuccessResponse;
import com.ppmtool.ppmtool.payload.LoginRequest;
import com.ppmtool.ppmtool.security.JwtTokenProvider;
import com.ppmtool.ppmtool.services.ControllerUtils;
import com.ppmtool.ppmtool.services.UserService;
import com.ppmtool.ppmtool.validator.UserValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

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

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest, BindingResult result) {
        var errors = controllerUtils.getInvalidObjectErrors(result);
        if (!errors.isEmpty()) {
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return ResponseEntity.ok(
                new JwtLoginSuccessResponse(
                        true,
                        "Bearer " + jwtTokenProvider.generateToken(authentication)
                )
        );
    }
}
