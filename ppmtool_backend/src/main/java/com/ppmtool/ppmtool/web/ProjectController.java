package com.ppmtool.ppmtool.web;

import com.ppmtool.ppmtool.domain.Project;
import com.ppmtool.ppmtool.services.ControllerUtils;
import com.ppmtool.ppmtool.services.ProjectService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/project")
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    @Autowired
    private ControllerUtils controllerUtils;

    @PostMapping("")
    public ResponseEntity<?> upsert(@Valid @RequestBody Project project, BindingResult result) {
        // binding result just gets populated for you... neat,
        // so it seems that binding result and the valid annotations just runs through the validation constraints
        // that don't need a trip to the database to validate. E.g. a string length is validated immediately
        // and the error object is returned with a good error message. But if you try to insert a new object with a
        // duplicate value for a unique field, a 500 uncaught error is thrown :boom:
        var errors = controllerUtils.getInvalidObjectErrors(result);
        if (!errors.isEmpty()) {
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(projectService.upsert(project), HttpStatus.CREATED);
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<?> getById(@PathVariable String projectId) {
        return new ResponseEntity<>(projectService.getById(projectId), HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<?> getAll() {
        return new ResponseEntity<>(projectService.getAll(), HttpStatus.OK);
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<Void> delete(@PathVariable String projectId) {
        projectService.delete(projectId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
