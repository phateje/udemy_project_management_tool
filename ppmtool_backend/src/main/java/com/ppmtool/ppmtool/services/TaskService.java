package com.ppmtool.ppmtool.services;

import com.ppmtool.ppmtool.domain.Backlog;
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

}
