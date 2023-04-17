package com.ppmtool.ppmtool.web;

import com.ppmtool.ppmtool.domain.Task;
import com.ppmtool.ppmtool.services.ControllerUtils;
import com.ppmtool.ppmtool.services.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/task")
@CrossOrigin
public class TaskController {

    @Autowired
    private TaskService taskService;
    @Autowired
    private ControllerUtils controllerUtils;

    @PostMapping("/{projectId}")
    public ResponseEntity<?> addTask(@Valid @RequestBody Task task, BindingResult result, @PathVariable String projectId) {
        var errors = controllerUtils.getInvalidObjectErrors(result);
        if (!errors.isEmpty()) {
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(taskService.addTask(projectId, task), HttpStatus.CREATED);
    }

    @GetMapping("/all/{projectId}")
    public ResponseEntity<?> getTasksByProject(@PathVariable String projectId) {
        return new ResponseEntity<>(taskService.getAllTasks(projectId), HttpStatus.OK);
    }

    @GetMapping("/{projectSequence}")
    public ResponseEntity<?> getTask(@PathVariable String projectSequence) {
        return new ResponseEntity<>(taskService.getByProjectSequence(projectSequence), HttpStatus.OK);

    }
}
