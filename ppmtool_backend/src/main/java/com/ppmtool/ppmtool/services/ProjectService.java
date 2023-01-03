package com.ppmtool.ppmtool.services;

import com.ppmtool.ppmtool.domain.Project;
import com.ppmtool.ppmtool.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;

    public Project upsert(Project project) {
        return projectRepository.save(project);
    }
}
