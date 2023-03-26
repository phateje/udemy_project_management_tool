package com.ppmtool.ppmtool.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Backlog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer tasksSequence = 0;
    private String projectId;

    // todo
    // 1 to 1 w project
    // 1 to many w tasks

    public Backlog() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTasksSequence(Integer tasksSequence) {
        this.tasksSequence = tasksSequence;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public Long getId() {
        return id;
    }

    public Integer getTasksSequence() {
        return tasksSequence;
    }

    public String getProjectId() {
        return projectId;
    }
}
