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

    // TODO - find a way to avoid nulling out fields that are not set on the task when this method is called I guess
    // for now this requires ALL fields to be set on the task if you want the update not to delete relevant data (e.g. the created date for one)
    // could also use a patch method and validate that in a post the id is always null but ehh
    public synchronized Task addTask(String projectId, Task task) {
        Backlog bl = backlogRepo.findByProjectId(projectId);
        if (bl == null) {
            throw new TaskException("couldn't find a backlog for project id " + projectId);
        }

        task.setBacklog(bl);

        // this doesn't really handle the case of a bullshit Id passed onto a new task
        // should probably check the database to make sure a task with that id exists and that
        // it belongs to the correct project but for now this'll do
        if (task.getId() == null) {
            Integer taskSequence = bl.getTasksSequence();
            taskSequence++;
            task.setProjectSequence(projectId + "_" + taskSequence);
            bl.setTasksSequence(taskSequence);
            task.setProjectId(projectId);
        }

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
