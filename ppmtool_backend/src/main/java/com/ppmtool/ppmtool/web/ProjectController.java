package com.ppmtool.ppmtool.web;

import com.ppmtool.ppmtool.domain.Project;
import com.ppmtool.ppmtool.services.ProjectService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.StringJoiner;

@RestController
@RequestMapping("/api/project")
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    @PostMapping("")
    public ResponseEntity<?> upsert(@Valid @RequestBody Project project, BindingResult result) {
        // binding result just gets populated for you... neat
        if (result.hasErrors()) {
            StringJoiner stringJoiner = new StringJoiner("\n - ");
            stringJoiner.add("Found the following issues with the project:");
            for (ObjectError error : result.getAllErrors()) {
                stringJoiner.add(error.getDefaultMessage());
            }
            return new ResponseEntity<String>(stringJoiner.toString(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Project>(projectService.upsert(project), HttpStatus.CREATED);
    }
}
