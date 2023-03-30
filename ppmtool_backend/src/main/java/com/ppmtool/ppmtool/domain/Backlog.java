package com.ppmtool.ppmtool.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class Backlog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer tasksSequence = 0;

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @OneToOne(mappedBy = "backlog")
    @JsonIgnore
    private Project project;

    // todo

    // 1 to many w tasks

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

}
