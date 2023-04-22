package com.ppmtool.ppmtool.services;

import com.ppmtool.ppmtool.domain.Backlog;
import com.ppmtool.ppmtool.domain.Project;
import com.ppmtool.ppmtool.domain.Task;
import com.ppmtool.ppmtool.repositories.BacklogRepository;
import com.ppmtool.ppmtool.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskService {
    @Autowired
    private BacklogRepository backlogRepo;

    @Autowired
    private TaskRepository taskRepo;

    public Task addTask(String projectId, Task task) {
        Backlog bl = backlogRepo.findByProjectId(projectId);
        if (bl == null) {
            throw new TaskException("couldn't find a backlog for project id " + projectId);
        }

        task.setBacklog(bl);

        Integer taskSequence = bl.getTasksSequence();
        taskSequence++;
        task.setProjectSequence(projectId + "_" + taskSequence);
        bl.setTasksSequence(taskSequence);
        task.setProjectId(projectId);

        if (task.getPriority() == null || task.getPriority() == 0) {
            task.setPriority(3);
        }
        if (task.getStatus() == null || task.getStatus().isBlank()) {
            task.setStatus("NEW");
        }

        backlogRepo.save(bl);
        taskRepo.save(task);
        return task;
    }

    public Iterable<Task> getAllTasks(String proejctId) {
        return taskRepo.findByProjectId(proejctId);
    }

    public Task getByProjectSequence(String projectSequence) {
        return taskRepo.findByProjectSequence(projectSequence);
    }

    public void deleteByProjectSequence(String projectSequence) {
        Task task = taskRepo.findByProjectSequence(projectSequence);
        if (task != null) {
            taskRepo.delete(task);
        }

    }

    public class TaskException extends RuntimeException {
        public TaskException(String message) {
            super(message);
        }
    }
}
