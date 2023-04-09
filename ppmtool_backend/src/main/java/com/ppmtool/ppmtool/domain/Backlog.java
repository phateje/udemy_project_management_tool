package com.ppmtool.ppmtool.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Backlog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer tasksSequence = 0;

    private String projectId;

    @OneToOne(mappedBy = "backlog")
    @JsonIgnore
    private Project project;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "backlog")
    private List<Task> tasks = new ArrayList<>();
    public Backlog() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTasksSequence(Integer tasksSequence) {
        this.tasksSequence = tasksSequence;
    }

    public Long getId() {
        return id;
    }

    public Integer getTasksSequence() {
        return tasksSequence;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }


    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

}
