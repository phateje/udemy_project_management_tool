package com.ppmtool.ppmtool.web;

import com.ppmtool.ppmtool.domain.Project;
import com.ppmtool.ppmtool.domain.Task;
import com.ppmtool.ppmtool.repositories.BacklogRepository;
import com.ppmtool.ppmtool.repositories.ProjectRepository;
import com.ppmtool.ppmtool.repositories.TaskRepository;
import com.ppmtool.ppmtool.services.ProjectService;
import com.ppmtool.ppmtool.services.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
    private BacklogRepository backlogRepository;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private TaskController taskController;



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
        assertThat(projectService.getById("test2").getProjectName()).isEqualTo("name2");
        assertThat(projectService.getById("test1").getProjectName()).isEqualTo("name1");
    }

    @Test
    void projectShouldGetBacklog() {
        Project project1 = new Project();
        project1.setProjectId("test1");
        project1.setProjectName("name1");

        projectService.upsert(project1);

        assertThat(backlogRepository.findAll().iterator().hasNext()).isTrue();
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

    @Test
    void tasksShouldGoOnProperProject() {
        Project p1 = new Project();
        p1.setProjectName("test1");
        p1.setProjectId("0001");
        projectService.upsert(p1);

        Project p2 = new Project();
        p2.setProjectName("test2");
        p2.setProjectId("0002");
        projectService.upsert(p2);

        Task task = new Task();
        task.setSummary("test");
        taskService.addTask("0001", task);
        task.setId(null);
        taskService.addTask("0001", task);
        task.setId(null);
        taskService.addTask("0001", task);
        task.setId(null);
        taskService.addTask("0002", task);
        task.setId(null);
        taskService.addTask("0002", task);

        var tasks = taskRepository.findAll().iterator();
        assertThat(tasks.next().getProjectSequence()).isEqualTo("0001_1");
        assertThat(tasks.next().getProjectSequence()).isEqualTo("0001_2");
        assertThat(tasks.next().getProjectSequence()).isEqualTo("0001_3");
        assertThat(tasks.next().getProjectSequence()).isEqualTo("0002_1");
        assertThat(tasks.next().getProjectSequence()).isEqualTo("0002_2");
    }

    @Test
    void taskGetterAllShouldReturnEmptyList() {
        var response = taskController.getTasksByProject("fakeId");
        assertThat(response.getBody()).isInstanceOf(Iterable.class);
        assertThat((Iterable<Task>) response.getBody()).isEmpty();
    }

    @Test
    void taskGetterAllShouldReturnSomething() {
        Project p1 = new Project();
        p1.setProjectName("test1");
        p1.setProjectId("0001");
        projectService.upsert(p1);

        Task task = new Task();
        task.setSummary("test");
        taskService.addTask("0001", task);

        var response = taskController.getTasksByProject("0001");
        assertThat(response.getBody()).isInstanceOf(Iterable.class);
        assertThat((Iterable<Task>) response.getBody()).isNotEmpty();
    }

    @Test
    void getTaskById() {
        Project p1 = new Project();
        p1.setProjectName("test1");
        p1.setProjectId("0001");
        projectService.upsert(p1);

        Task task = new Task();
        task.setSummary("test");
        taskService.addTask("0001", task);

        assertThat(((Task) taskController.getTask("0001_1").getBody()).getSummary()).isEqualTo("test");
    }

    @Test
    void getTaskByIdShouldErrorOut() {
        ResponseEntity<?> res = taskController.getTask("fake");

        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(((Map<String, List<String>>)res.getBody()).get("error").get(0)).isEqualTo("fake not found");
    }

    @Test
    void deleteById() {
        Project p1 = new Project();
        p1.setProjectName("test1");
        p1.setProjectId("0001");
        projectService.upsert(p1);

        Task task = new Task();
        task.setSummary("test");
        taskService.addTask("0001", task);

        assertThat(taskRepository.count()).isEqualTo(1);
        assertThat(taskController.deleteTask("0001_1").getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(taskRepository.count()).isEqualTo(0);
    }

    @Test
    void deleteById2() {
        Project p1 = new Project();
        p1.setProjectName("test1");
        p1.setProjectId("0001");
        projectService.upsert(p1);

        Task task = new Task();
        task.setSummary("test");
        taskService.addTask("0001", task);

        assertThat(taskRepository.count()).isEqualTo(1);
        assertThat(taskController.deleteTask("fake").getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(taskRepository.count()).isEqualTo(1);
    }

}
