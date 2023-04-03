package com.ppmtool.ppmtool.relationshipexperiments;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RootRepo extends CrudRepository<RootEnt, Long> {
}
