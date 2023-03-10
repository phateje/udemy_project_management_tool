package com.ppmtool.ppmtool.web;

import com.ppmtool.ppmtool.domain.Project;
import com.ppmtool.ppmtool.repositories.ProjectRepository;
import com.ppmtool.ppmtool.services.ProjectService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.validation.BindingResult;

import java.util.Iterator;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("test")
@SpringBootTest
public class ProjectControllerTest {
    @Autowired
    private ProjectController projectController;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private ProjectService projectService;

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
        assertThat(projectRepository.findById(1l).get().getProjectName().equals("name")).isTrue();


        final Project projectEx = new Project();
        projectEx.setProjectId("test");
        projectEx.setProjectName("name");
        assertThatThrownBy(() -> projectController.upsert(projectEx, result))
                .isInstanceOf(ProjectService.ProjectException.class);
    }

    @Test
    void shouldRetrieveCorrectProjectById() {
        Project project1 = new Project();
        project1.setProjectId("test1");
        project1.setProjectName("name1");
        Project project2 = new Project();
        project2.setProjectId("test2");
        project2.setProjectName("name2");
        projectRepository.save(project1);
        projectRepository.save(project2);

        assertThat(projectRepository.count() == 2).isTrue();
        assertThat(projectService.getById("test2").getProjectName().equals("name2")).isTrue();
    }

    @Test
    void shouldRetrieveCorrectProjectByName() {
        Project project1 = new Project();
        project1.setProjectId("test1");
        project1.setProjectName("name1");
        Project project2 = new Project();
        project2.setProjectId("test2");
        project2.setProjectName("name1");
        projectRepository.save(project1);
        projectRepository.save(project2);

        assertThat(projectRepository.count() == 2).isTrue();
        assertThat(projectRepository.findByProjectName("name1").size() == 2).isTrue();
        for (Project p : projectRepository.findByProjectName("name1")) {
            assertThat(p.getProjectName().equals("name1")).isTrue();
        }
    }

    @Test
    void controllerGetProjectOrNull() {
        var resp = (ResponseEntity<Project>) projectController.getById("test");
        assertThat(resp.getBody() == null).isTrue();

        Project project = new Project();
        project.setProjectId("test");
        project.setProjectName("name");
        projectRepository.save(project);

        resp = (ResponseEntity<Project>) projectController.getById("test");
        assertThat(resp.getBody().getProjectName().equals("name")).isTrue();
    }

    @Test
    void getAllProjects() {
        Project project = new Project();
        project.setProjectId("test");
        project.setProjectName("name");
        projectRepository.save(project);
        project = new Project();
        project.setProjectId("test2");
        project.setProjectName("name2");
        projectRepository.save(project);

        var resp = (ResponseEntity<Iterable<Project>>) projectController.getAll();
        Iterator<Project> itor = resp.getBody().iterator();
        assertThat(itor.next() != null).isTrue();
        assertThat(itor.next() != null).isTrue();
        assertThat(itor.hasNext() == false).isTrue();
    }

    @Test
    void deleteProject() {
        Project project = new Project();
        project.setProjectId("test");
        project.setProjectName("name");
        projectRepository.save(project);
        assertThat(projectRepository.count() == 1).isTrue();

        projectController.delete("tests");
        assertThat(projectRepository.count() == 1).isTrue();

        projectController.delete("test");
        assertThat(projectRepository.count() == 0).isTrue();
    }
}
