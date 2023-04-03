package com.ppmtool.ppmtool.relationshipexperiments;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OneToOneRepo extends CrudRepository<OneToOneEnt, Long> {
}
