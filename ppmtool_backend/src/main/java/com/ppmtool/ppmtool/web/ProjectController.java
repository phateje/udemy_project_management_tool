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

import java.security.Principal;

@RestController
@RequestMapping("/api/project")
@CrossOrigin
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    @Autowired
    private ControllerUtils controllerUtils;

    // used for tests only right now
    // @PostMapping("")
    public ResponseEntity<?> upsert(@Valid @RequestBody Project project, BindingResult result) {
        return this.upsert(project, result, null);
    }

    @PostMapping("")
    public ResponseEntity<?> upsert(@Valid @RequestBody Project project, BindingResult result, Principal principal) {
        // binding result just gets populated for you... neat,
        // so it seems that binding result and the valid annotations just runs through the validation constraints
        // that don't need a trip to the database to validate. E.g. a string length is validated immediately
        // and the error object is returned with a good error message. But if you try to insert a new object with a
        // duplicate value for a unique field, a 500 uncaught error is thrown :boom:
        var errors = controllerUtils.getInvalidObjectErrors(result);
        if (!errors.isEmpty()) {
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        String username = principal != null ? principal.getName() : null;
        return new ResponseEntity<>(projectService.upsert(project, username), HttpStatus.CREATED);
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<?> getById(@PathVariable String projectId, Principal principal) {
        return new ResponseEntity<>(projectService.getById(projectId, principal != null ? principal.getName() : ""), HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<?> getAll(Principal principal) {
        return new ResponseEntity<>(projectService.getAll(principal != null ? principal.getName() : ""), HttpStatus.OK);
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<Void> delete(@PathVariable String projectId, Principal principal) {
        projectService.delete(projectId, principal != null ? principal.getName() : "");
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
