package com.ppmtool.ppmtool.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Date;

@Entity
public class Project {
    // should really find a way to abstract this thing away and hide it from the FE if it ain't needed
    // https://stackoverflow.com/questions/61008760/spring-boot-how-do-i-hide-or-prevent-from-loading-some-properties-of-an-entity
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Need to provide a project name")
    private String projectName;
    @NotBlank(message = "Need to provide a project id")
    @Size(min=4, max=6, message = "4 to 6 char id allowed")
    @Column(updatable = false, unique = true)
    private String projectId;
    private String description;
    private Date startDate;
    private Date endDate;

    public Backlog getBacklog() {
        return backlog;
    }

    public void setBacklog(Backlog backlog) {
        this.backlog = backlog;
    }

    @Column(updatable = false)
    private Date createdAt;
    private Date updatedAt;

    // followed this one instead, much simpler https://www.baeldung.com/jpa-one-to-one
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="backlog_id", referencedColumnName = "id")
    private Backlog backlog;


    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @PrePersist
    protected void onCreate(){
        this.createdAt = new Date();
    }

    @PreUpdate
    protected void onUpdate(){
        this.updatedAt = new Date();
    }
}
