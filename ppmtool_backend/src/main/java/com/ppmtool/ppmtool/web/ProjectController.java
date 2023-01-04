package com.ppmtool.ppmtool.web;

import com.ppmtool.ppmtool.domain.Project;
import com.ppmtool.ppmtool.services.ControllerUtils;
import com.ppmtool.ppmtool.services.ProjectService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

@RestController
@RequestMapping("/api/project")
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    @Autowired
    private ControllerUtils controllerUtils;

    @PostMapping("")
    public ResponseEntity<?> upsert(@Valid @RequestBody Project project, BindingResult result) {
        // binding result just gets populated for you... neat
        // so it seems that binding result and the valid annotations just runs through the validation constraints
        // that don't need a trip to the database to validate. E.g. a string length is validated immediately
        // and the error object is returned with a good error message. But if you try to insert a new object with a
        // duplicate value for a unique field, a 500 uncaught error is thrown :boom:
        var errors = controllerUtils.getInvalidObjectErrors(result);
        if (errors.isEmpty() == false) {
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Project>(projectService.upsert(project), HttpStatus.CREATED);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public class ProjectException extends Exception {
        public ProjectException(String message) {
            super(message);
        }
    }
}
