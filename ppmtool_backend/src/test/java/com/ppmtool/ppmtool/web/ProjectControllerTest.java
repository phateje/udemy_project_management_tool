package com.ppmtool.ppmtool.web;

import com.ppmtool.ppmtool.domain.Project;
import com.ppmtool.ppmtool.repositories.ProjectRepository;
import com.ppmtool.ppmtool.services.ProjectService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.validation.BindingResult;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@SpringBootTest
public class ProjectControllerTest {
    @Autowired
    private ProjectController projectController;
    @Autowired
    private ProjectRepository projectRepository;

    @Test
    void contextLoads() {
        assertThat(projectController).isNotNull();
    }

    @Test
    void shouldInsertProjectThenCrapOutForUniqueIdConstraint() {
        Project project = new Project();
        project.setProjectId("test");
        project.setProjectName("name");

        BindingResult result = mock(BindingResult.class);
        when(result.hasErrors()).thenReturn(false);
        projectController.upsert(project, result);
        assertThat(projectRepository.findById(1l).get().getProjectName() == "Name");


        final Project projectEx = new Project();
        projectEx.setProjectId("test");
        projectEx.setProjectName("name");
        assertThatThrownBy(() -> projectController.upsert(projectEx, result))
                .isInstanceOf(ProjectService.ProjectException.class);
    }
}
