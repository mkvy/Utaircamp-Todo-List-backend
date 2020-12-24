package ru.utair.student2.todo.demo.service;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.utair.student2.todo.demo.entity.Task;
import ru.utair.student2.todo.demo.exception.NotFoundException;
import ru.utair.student2.todo.demo.repository.TaskRepository;

import java.lang.invoke.SerializedLambda;
import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)

public class TaskServiceTest {

    //вместо этого объекта Mock
    @Mock
    private TaskRepository taskRepository;

    //конструктор
//    @InjectMocks
    //private TaskService taskService = new TaskService(taskRepository);
    @InjectMocks
    private TaskService taskService;

    private final Task task = Task.builder()
            .id(100L)
            .text("TASK_TEST")
            .build();

    //создаем экземпляр классса
    @Test
    public void findAll() {
        //если найдено, то возвращает список из одного объекта
        //вместо всей базы
        //подготавливаем данные
        when(taskRepository.findAll()).thenReturn(Collections.singletonList(task));
        //выполним тестируемый метод
        List<Task> result = taskService.findAll();
        //проверяем результат
        assertThat(result, hasSize(1));
        assertThat(result.get(0), equalTo(task));
    }

    @Test
    public void findById() {
        //0L - > по 0L выдает task 100l
        when(taskRepository.findById(0L)).thenReturn(java.util.Optional.ofNullable(task));
        Task result = taskService.findById(0L);
        assertThat(result, equalTo(task));
    }

    @Test
    public void findByIdException() {
        //taskRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
        when(taskRepository.findById(eq(0L))).thenReturn(java.util.Optional.empty());

        try {
            Task result = taskService.findById(0L);
        } catch (NotFoundException e) {
            assertThat("Could not find object with id = 0", equalTo(e.getMessage()));
            return;
        } catch (Throwable e) {
            fail();
        }
        fail();
    }

    @Test
    public void addTest() {
        when(taskRepository.save(eq(task))).thenReturn(task);
        Task result = taskService.add(task);
        verify(taskRepository, times(1)).save(task);
        //может правильнее дальше result(хотя одинаковые)
        assertNotNull(task.getCreateDate());
        Calendar calendar = new GregorianCalendar();
        calendar.add(Calendar.SECOND,-1);
        assertThat(task.getCreateDate(),greaterThan(calendar.getTime()));
    }

    @Test
    public void updateTest() {
        //findById();

        when(taskRepository.findById(0L)).thenReturn(java.util.Optional.ofNullable(task));

        when(taskRepository.save(eq(task))).thenReturn(task);
        //Task taskBeforeSave = task;
        task.setText("Test2");

        Task result = taskService.save(task,0L);

        verify(taskRepository,times(1)).save(task);
        assertNotNull(result.getUpdateDate());
        Calendar calendar = new GregorianCalendar();
        calendar.add(Calendar.SECOND,-1);
        assertThat(task.getUpdateDate(),greaterThan(calendar.getTime()));

     //   assertThat(result.getText(),not(equalTo(taskBeforeSave.getText())));
    }

    @Test
    public void deleteTest() {
        findById();
        doNothing().when(taskRepository).delete(eq(task));
        taskService.delete(0L);
        verify(taskRepository,times(1)).delete(task);
    }

    @Test
    public void deleteTestException() {
        findByIdException();
    }
    @Test
    public void updateTestException() {
        findByIdException();
    }
}