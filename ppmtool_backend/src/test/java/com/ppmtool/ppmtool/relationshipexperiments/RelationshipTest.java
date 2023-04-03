package com.ppmtool.ppmtool.relationshipexperiments;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class RelationshipTest {
    @Autowired
    private RootRepo rootRepository;

    @Test
    void testInsertAndDelete() {
        assertThat(rootRepository.count()).isEqualTo(0);
        rootRepository.save(new RootEnt());
        assertThat(rootRepository.count()).isEqualTo(1);
        rootRepository.deleteAll();
        assertThat(rootRepository.count()).isEqualTo(0);
    }

}
