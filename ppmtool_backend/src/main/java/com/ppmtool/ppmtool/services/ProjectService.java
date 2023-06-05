package com.ppmtool.ppmtool.services;

import com.ppmtool.ppmtool.domain.Backlog;
import com.ppmtool.ppmtool.domain.Project;
import com.ppmtool.ppmtool.repositories.BacklogRepository;
import com.ppmtool.ppmtool.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    public Project upsert(Project project) {
        return this.upsert(project, null);
    }
    public Project upsert(Project project, String username) {
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

            if (username != null && !username.isBlank()) {
                // TODO could throw exception, ok for now
                // could also overwrite the user if a different user somehow updates. Also ok for now
                project.setUser(customUserDetailsService.loadUserByUsername(username));
            }

            return projectRepository.save(project);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            throw new ProjectException("projectId " + project.getProjectId() + " already exists in the database");
        }
    }

    public Project getById(String projectId, String username) {
        return projectRepository.findByProjectIdAndUserUsername(projectId, username);
    }

    // should order by last modified date desc for convenience
    public Iterable<Project> getAll(String username) {
        return projectRepository.findByUserUsername(username);
    }

    public void delete(String projectId, String username) {
        Project project = projectRepository.findByProjectIdAndUserUsername(projectId, username);
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
