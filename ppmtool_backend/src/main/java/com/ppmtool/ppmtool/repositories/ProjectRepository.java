package com.ppmtool.ppmtool.repositories;


import com.ppmtool.ppmtool.domain.Project;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Long> {
    Project findByProjectIdAndUserUsername(String projectId, String username); //findByProjectId is automagically implemented by the framework, intellisense SHOULD show it, it didn't for me but whatevs

    Iterable<Project> findByUserUsername(String username);

    // yay, just like above if you declare "findByName it breaks cause the property doesn't exist
    // projectName though does, so this gets autoimplemented.
    // WARNING, intellij's refactor functionality doesn't seem to be picking this method up tho
    // so that might cause stuff to break. More reason to have tests I guess :)
    List<Project> findByProjectName(String projectName);

}
