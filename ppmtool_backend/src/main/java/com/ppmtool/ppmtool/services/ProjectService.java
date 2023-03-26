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
            if (project.getId() == null) {
                Backlog backlog = new Backlog();
                backlog.setProject(project);
                project.setBacklog(backlog);
            }

            // todo the course adds a query in this method to return the backlog with the project
            // you just updated (not gonna be set on update, just on insert)
            // that feels really wrong in so many ways, and if anything it should be handled with another
            // query outside of this method, either at the service level, or by the client itself, but whatever

            // todo see if the repository can find a backlog by project join instead of redundantly paste the project custom id on the backlog as well

            return projectRepository.save(project);
        } catch (Exception ex) {
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
