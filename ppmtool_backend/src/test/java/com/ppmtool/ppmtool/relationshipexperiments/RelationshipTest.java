package com.ppmtool.ppmtool.relationshipexperiments;

import org.hibernate.TransientPropertyValueException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;


import static org.assertj.core.api.Assertions.assertThat;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest
public class RelationshipTest {
    @Autowired
    private RootRepo rootRepository;
    @Autowired
    private OneToOneRepo oneToOneRepo;

    @Test
    void testInsertAndDelete() {
        assertThat(rootRepository.count()).isEqualTo(0);
        rootRepository.save(new RootEnt());
        assertThat(rootRepository.count()).isEqualTo(1);
        rootRepository.deleteAll();
        assertThat(rootRepository.count()).isEqualTo(0);
    }

    @Test
    void testInsertCascadeAll() {
        assertThat(rootRepository.count()).isEqualTo(0);
        assertThat(oneToOneRepo.count()).isEqualTo(0);

        RootEnt root = new RootEnt();
        root.singleChildCascadeAll = new OneToOneEnt();

        // cascade all causes this save to also save the child
        rootRepository.save(root);

        assertThat(rootRepository.count()).isEqualTo(1);
        assertThat(oneToOneRepo.count()).isEqualTo(1);
    }

    @Test
    void testInsertCascadeAllFromChild() {
        assertThat(rootRepository.count()).isEqualTo(0);
        assertThat(oneToOneRepo.count()).isEqualTo(0);

        OneToOneEnt child = new OneToOneEnt();
        child.root = new RootEnt();

        // cascade all causes this save to also save the child
        try {
            oneToOneRepo.save(child);
            assertThat(false).isTrue().withFailMessage("should fail");
        } catch (Exception ex) {
            assertThat(ex.getMessage().contains("object references an unsaved transient instance"));
        }

        assertThat(rootRepository.count()).isEqualTo(0);
        assertThat(oneToOneRepo.count()).isEqualTo(0);
    }

    @Test
    void testQuery() {
        assertThat(rootRepository.count()).isEqualTo(0);
        assertThat(oneToOneRepo.count()).isEqualTo(0);

        RootEnt root = new RootEnt();
        root.singleChildCascadeAll = new OneToOneEnt();
        root.singleChildCascadeAll.name = "the child";
        rootRepository.save(root);

        root = rootRepository.findById(1L).get();
        assertThat(root.singleChildCascadeAll.name).isEqualTo("the child");

        OneToOneEnt child = oneToOneRepo.findById(1L).get();
        child.name = "updated";
        oneToOneRepo.save(child);

        root = rootRepository.findById(1L).get();
        assertThat(root.singleChildCascadeAll.name).isEqualTo("updated");
    }

    @Test
    void testDeletion() {
        assertThat(rootRepository.count()).isEqualTo(0);
        assertThat(oneToOneRepo.count()).isEqualTo(0);

        RootEnt root = new RootEnt();
        root.singleChildCascadeAll = new OneToOneEnt();
        rootRepository.save(root);

        assertThat(rootRepository.count()).isEqualTo(1);
        assertThat(oneToOneRepo.count()).isEqualTo(1);

        try {
            // failing since adding mappedby property on the child.
            oneToOneRepo.deleteById(1L);
            assertThat(false).isTrue();
        } catch (Exception e) {
            assertThat(e.getMessage()).contains("could not execute statement; SQL [n/a]; constraint");
        }

        assertThat(rootRepository.count()).isEqualTo(1);
        assertThat(oneToOneRepo.count()).isEqualTo(1);
    }

    @Test
    void testClearRelationshipNotEntity() {
        assertThat(rootRepository.count()).isEqualTo(0);
        assertThat(oneToOneRepo.count()).isEqualTo(0);

        RootEnt root = new RootEnt();
        root.singleChildCascadeAll = new OneToOneEnt();
        rootRepository.save(root);

        assertThat(rootRepository.count()).isEqualTo(1);
        assertThat(oneToOneRepo.count()).isEqualTo(1);
        assertThat(rootRepository.findById(1L).get().singleChildCascadeAll).isNotNull();
        assertThat(oneToOneRepo.findById(1L).get().root).isNotNull(); // requires (mappedBy = "singleChildCascadeAll") on the child relationship!

        root.singleChildCascadeAll = null;
        rootRepository.save(root);

        assertThat(rootRepository.count()).isEqualTo(1);
        assertThat(oneToOneRepo.count()).isEqualTo(1);
        assertThat(rootRepository.findById(1L).get().singleChildCascadeAll).isNull();
        assertThat(oneToOneRepo.findById(1L).get().root).isNull();
    }
}
