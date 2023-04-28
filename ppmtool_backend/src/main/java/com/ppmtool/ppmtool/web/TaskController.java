package com.ppmtool.ppmtool.web;

import com.ppmtool.ppmtool.domain.Task;
import com.ppmtool.ppmtool.repositories.TaskRepository;
import com.ppmtool.ppmtool.services.ControllerUtils;
import com.ppmtool.ppmtool.services.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/task")
@CrossOrigin
public class TaskController {

    @Autowired
    private TaskService taskService;
    @Autowired
    private ControllerUtils controllerUtils;

    @Autowired
    private TaskRepository taskRepository;

    @PostMapping("/{projectId}")
    public ResponseEntity<?> addTask(@Valid @RequestBody Task task, BindingResult result, @PathVariable String projectId) {
        var errors = controllerUtils.getInvalidObjectErrors(result);
        if (!errors.isEmpty()) {
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        taskService.addTask(projectId, task);
        return new ResponseEntity<>(taskRepository.findById(task.getId()), HttpStatus.CREATED);
    }

    @GetMapping("/all/{projectId}")
    public ResponseEntity<?> getTasksByProject(@PathVariable String projectId) {
        // todo throw exceptions with errors if query returns nothing? (no: empty list is valid)
        return new ResponseEntity<>(taskService.getAllTasks(projectId), HttpStatus.OK);
    }

    @GetMapping("/{projectSequence}")
    public ResponseEntity<?> getTask(@PathVariable String projectSequence) {
        // todo throw exceptions with errors if query returns nothing? (yes)
        Task t = taskService.getByProjectSequence(projectSequence);
        if (t == null) {
            Map<String, List<String>> res = new HashMap<>();
            res.put("error", Arrays.asList(projectSequence + " not found"));
            return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(taskService.getByProjectSequence(projectSequence), HttpStatus.OK);
    }

    @DeleteMapping("/{projectSequence}")
    public ResponseEntity<?> deleteTask(@PathVariable String projectSequence) {
        taskService.deleteByProjectSequence(projectSequence);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
