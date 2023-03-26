package com.ppmtool.ppmtool.repositories;

import com.ppmtool.ppmtool.domain.Task;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends CrudRepository<Task, Long> {
}
