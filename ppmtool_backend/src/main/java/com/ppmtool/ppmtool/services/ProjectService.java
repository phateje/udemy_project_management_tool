package com.ppmtool.ppmtool.services;

import com.ppmtool.ppmtool.domain.Project;
import com.ppmtool.ppmtool.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;

    public Project upsert(Project project) {
        try {
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
