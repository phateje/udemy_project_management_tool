package com.ppmtool.ppmtool.repositories;

import com.ppmtool.ppmtool.domain.Task;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends CrudRepository<Task, Long> {
    List<Task> findByProjectId(String projectId);

    Task findByProjectSequence(String projectSequence);

}
