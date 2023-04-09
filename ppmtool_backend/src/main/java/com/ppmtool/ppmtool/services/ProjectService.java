package com.ppmtool.ppmtool.services;

import com.ppmtool.ppmtool.domain.Backlog;
import com.ppmtool.ppmtool.domain.Project;
import com.ppmtool.ppmtool.repositories.BacklogRepository;
import com.ppmtool.ppmtool.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private BacklogRepository backlogRepository;

    public Project upsert(Project project) {
        try {
            // TODO - if a requester sends a shit project with a fake Id
            // it gets inserted, but no backlog gets created for it because of
            // this clause. fun.
            // Should write a test case for this
            if (project.getId() == null) {
                Backlog backlog = new Backlog();
                backlog.setProject(project);
                backlog.setProjectId(project.getProjectId());
                project.setBacklog(backlog);
            }

            return projectRepository.save(project);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            throw new ProjectException("projectId " + project.getProjectId() + " already exists in the database");
        }
    }

    public Project getById(String projectId) {
        return projectRepository.findByProjectId(projectId);
    }

    // should order by last modified date desc for convenience
    public Iterable<Project> getAll() {
        return projectRepository.findAll();
    }

    public void delete(String projectId) {
        Project project = projectRepository.findByProjectId(projectId);
        if (project != null) {
            projectRepository.deleteById(project.getId());
        }
    }

    //https://www.baeldung.com/exception-handling-for-rest-with-spring
    public class ProjectException extends RuntimeException {
        public ProjectException(String message) {
            super(message);
        }
    }
}
