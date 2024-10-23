package exercise.controller;

import java.util.List;

import exercise.dto.TaskCreateDTO;
import exercise.dto.TaskDTO;
import exercise.dto.TaskUpdateDTO;
import exercise.mapper.TaskMapper;
import exercise.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import exercise.exception.ResourceNotFoundException;
import exercise.repository.TaskRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/tasks")
public class TasksController {
    // BEGIN
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TaskMapper taskMapper;

    @GetMapping("")
    public List<TaskDTO> index() {
        var tasks = taskRepository.findAll().stream()
                .map(p -> taskMapper.map(p))
                .toList();

        return tasks;
    }

    @GetMapping("/{id}")
    public TaskDTO show(@PathVariable Long id) {
        var task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(""));
        return taskMapper.map(task);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public TaskDTO create(@RequestBody TaskCreateDTO dto) {
        var assignee = userRepository.findById(dto.getAssigneeId())
                .orElseThrow(() -> new ResourceNotFoundException(""));
        var task = taskMapper.map(dto);

        assignee.addTask(task);
        userRepository.save(assignee);

        return taskMapper.map(task);
    }

    @PutMapping("/{id}")
    public TaskDTO update(@PathVariable Long id, @RequestBody TaskUpdateDTO dto) {
        var task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(""));

        var assigneeNew = userRepository.findById(dto.getAssigneeId())
                .orElseThrow(() -> new ResourceNotFoundException(""));

        var assigneeOld = task.getAssignee();

        taskMapper.update(dto, task);
        assigneeOld.removeTask(task);
        assigneeNew.addTask(task);
        userRepository.save(assigneeOld);
        userRepository.save(assigneeNew);

        return taskMapper.map(task);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        var task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(""));

        var assignee = task.getAssignee();
        assignee.removeTask(task);
        userRepository.save(assignee);
    }

    // END
}
//GET /tasks – просмотр списка всех задач
//        GET /tasks/{id} – просмотр конкретной задачи
//        POST /tasks – создание новой задачи
//        PUT /tasks/{id} – редактирование задачи. При редактировании мы должны иметь возможность поменять название, описание задачи и ответственного разработчика
//        DELETE /tasks/{id} – удаление задачи