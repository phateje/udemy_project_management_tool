package com.ppmtool.ppmtool.repositories;


import com.ppmtool.ppmtool.domain.Project;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Long> {
    Project findByProjectId(String projectId);
}
