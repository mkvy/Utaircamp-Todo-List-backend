package ru.utair.student2.todo.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.utair.student2.todo.demo.entity.Task;

import java.util.List;


//task - сам класс , long - идентификатор
//работа с бд
@Repository
public interface TaskRepository extends JpaRepository<Task, Long>, JpaSpecificationExecutor<Task> {

    public List<Task> findAllByIsComplete(Boolean isComplete);
    public List<Task> findTaskByTextIgnoreCaseContaining(String subText);
    public List<Task> findTaskByTextIgnoreCaseContains(String subText);
    public List<Task> findAllByIsCompleteOrderByCreateDateAsc(Boolean is);
}
