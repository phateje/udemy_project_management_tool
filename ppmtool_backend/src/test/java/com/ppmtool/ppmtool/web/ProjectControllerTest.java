package com.ppmtool.ppmtool.web;

import com.ppmtool.ppmtool.domain.Project;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.BindingResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.

@SpringBootTest
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ProjectControllerTest {

    @Autowired
    private ProjectController projectController;

    @Test
    void contextLoads() {
        assertThat(projectController).isNotNull();
    }

    @Test
    void shouldInsertProject() {
        Project project = new Project();
        project.setProjectId("test");
        project.setProjectName("name");

        BindingResult result = mock(BindingResult.class);
        when(result.hasErrors()).thenReturn(false);
        projectController.upsert(project, result);

        https://1kevinson.com/how-to-write-integration-tests-with-h2-in-memory-database-and-springboot/
    }
}
