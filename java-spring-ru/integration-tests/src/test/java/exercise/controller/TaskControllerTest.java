package exercise.controller;

import org.junit.jupiter.api.Test;
import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;
import org.instancio.Instancio;
import org.instancio.Select;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import java.util.HashMap;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.datafaker.Faker;
import exercise.repository.TaskRepository;
import exercise.model.Task;

// BEGIN

// END
@SpringBootTest
@AutoConfigureMockMvc
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Faker faker;

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TaskRepository taskRepository;


    @Test
    public void testWelcomePage() throws Exception {
        var result = mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andReturn();

        var body = result.getResponse().getContentAsString();
        assertThat(body).contains("Welcome to Spring!");
    }

    @Test
    public void testIndex() throws Exception {
        var result = mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andReturn();

        var body = result.getResponse().getContentAsString();
        assertThatJson(body).isArray();
    }


    // BEGIN
    @Test
    public void testShow() throws Exception {
        var testData = createDataForTest();

        var result = mockMvc.perform(get("/tasks/" + testData.getId()))
                .andExpect(status().isOk())
                .andReturn();

        var body = result.getResponse().getContentAsString();
        var expected = om.writeValueAsString(testData);

        assertThatJson(body).isEqualTo(expected);
    }

    @Test
    public void testCreate() throws Exception {
        var testData = new HashMap<>();
        testData.put("title", "title");
        testData.put("description", "description");

        var request = post("/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(testData));

        var result = mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        var task = om.readValue(result, Task.class);
        var expected = om.writeValueAsString(taskRepository.findById(task.getId()));
        //assertThatJson(result).isEqualTo(expected);
        assertThatJson(result).and(
                a -> a.node("title").isEqualTo("title"),
                a -> a.node("description").isEqualTo("description"));
    }

    @Test
    public void testUpdate() throws Exception {
        var testData = createDataForTest();

        var dataForUpdate = new HashMap<>();
        dataForUpdate.put("title", "title");

        var request = put("/tasks/" + testData.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(dataForUpdate));

        mockMvc.perform(request)
                .andExpect(status().isOk());

        var task = taskRepository.findById(testData.getId()).get();
        assertThat(task.getTitle()).isEqualTo("title");
    }

    @Test
    public void testDelete() throws Exception {
        var testData = createDataForTest();

        var request = delete("/tasks/" + testData.getId());

        mockMvc.perform(request)
                .andExpect(status().isOk());

        var task = taskRepository.findById(testData.getId());
        assertThat(task).isEmpty();
    }

    private Task createDataForTest() {
        var taskData = Instancio.of(Task.class)
                .ignore(Select.field(Task::getId))
                .supply(Select.field(Task::getTitle), () -> faker.lorem().word())
                .supply(Select.field(Task::getDescription), () -> faker.lorem().word())
                .ignore(Select.field(Task::getCreatedAt))
                .ignore(Select.field(Task::getUpdatedAt))
                .create();
        return taskRepository.save(taskData);
    }
    // END
}
