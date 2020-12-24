package ru.utair.student2.todo.demo.repository;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.utair.student2.todo.demo.TodoprojApplication;
import ru.utair.student2.todo.demo.entity.Task;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TodoprojApplication.class)
public class TaskRepositoryTest {

    @Autowired //инжектим taskrepository
    private TaskRepository taskRepository;
    private final Task task = Task.builder()
            .id(100L)
            .text("TASK_TEXT")
            .build();


    //чтобы после каждого теста очищалась бд
    @AfterEach
    public void clear() {
        taskRepository.deleteAll();
    }


    @Test
    public void findTaskByTextIgnoreCaseContaining() {
      //  taskRepository.save(Task.builder().id(10L).text("tasktwo").build());
        taskRepository.save(task);

        List<Task> result = taskRepository.findTaskByTextIgnoreCaseContaining("ask");


        assertThat(result,hasSize(1));
        assertThat(result.get(0).getText(),equalTo(task.getText()));
    }
    @Test
    public void findTaskByTextIgnoreCaseContains1() {
        taskRepository.save(task);

        List<Task> result = taskRepository.findTaskByTextIgnoreCaseContains("ask");


        assertThat(result,hasSize(1));
        assertThat(result.get(0).getText(),equalTo(task.getText()));
    }

    @Test
    public void searchTest3() {

    }

}
