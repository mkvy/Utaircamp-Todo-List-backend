package ru.utair.student2.todo.demo.service;

import lombok.AllArgsConstructor;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import ru.utair.student2.todo.demo.dto.FindParams;
import ru.utair.student2.todo.demo.entity.Tag;
import ru.utair.student2.todo.demo.entity.Task;
import ru.utair.student2.todo.demo.exception.NotFoundException;
import ru.utair.student2.todo.demo.repository.TaskRepository;

import javax.persistence.criteria.Join;
import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Stream;

import javax.persistence.criteria.Predicate;
@Service
@Transactional
@AllArgsConstructor

public class TaskService {
    private final TaskRepository taskRepository;
    private final TagService tagService;


    public Task findById(Long id) {
        //Optional - либо объект Task, либо NULL
        return taskRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public List<Task> findByParam(FindParams findParams) {
       return taskRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
            criteriaQuery.orderBy(criteriaBuilder.desc(root.get("createDate")));
            List<Predicate> predicates = new ArrayList<>();

            if (findParams.getIsComplete() != null) {
                predicates.add(criteriaBuilder.equal(root.get("isComplete"),findParams.getIsComplete()));
            }
            predicates.add(criteriaBuilder.isNull(root.get("parentId")));

            if (!CollectionUtils.isEmpty((findParams.getTags()))) {
                Join<Task, Tag> tags = root.join("tags");
                predicates.add(criteriaBuilder.or(
                        findParams.getTags()
                        .stream()
                        .map(tag -> criteriaBuilder.equal(tags.get("id"),tag.getId())). //map из тега делает предикат
                                toArray(Predicate[]::new)
                ));
                criteriaQuery.groupBy(root.get("id"));
          /*      findParams.getTags().forEach(tag -> {
                    Tag tagDB = tagService.findById(tag.getId());
                    tagDB.getTaskSet().forEach(task-> {
                        task.setTags(null);
                    });
                    predicates.add(criteriaBuilder.in(root.get("tags").get("tag_id")).value(tagDB));
                });*/
            }

            //stringutils isnotempty проверит сразу на null и на пустоту
            if (StringUtils.isNotEmpty(findParams.getText())) {
            /*    for (String key : findParams.getText().toUpperCase().trim().split("[^a-zA-Zа-яА-ЯёЁ\\d]")) {
                    if (key.length()>=2) {
                        predicates.add(criteriaBuilder.like(criteriaBuilder.upper(root.get("text")),"%" + key + "%"));
                    }
                }*/

                Stream.of(findParams.getText().toUpperCase().trim().split("[^a-zA-Zа-яА-ЯёЁ\\d]")).forEach(key -> {
                    if (key.length()>=2) {
                        predicates.add(criteriaBuilder.like(criteriaBuilder.upper(root.get("text")),"%" + key + "%"));
                    }
                });
            }
/*
            if (findParams.getText() != null) {

                String str =("%"+ findParams.getText() + "%").toLowerCase(Locale.ROOT);
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("text")),str));
            }*/

            if (findParams.getAfterCreateDate() != null) {
                predicates.
                        add(criteriaBuilder.
                                greaterThanOrEqualTo(root.get("createDate"),findParams.getAfterCreateDate()));
            }
           if (findParams.getBeforeCreateDate() != null) {
               predicates.
                       add(criteriaBuilder.
                               lessThanOrEqualTo(root.get("createDate"),findParams.getBeforeCreateDate()));
           }

//text с like добавить
            return criteriaBuilder.and(predicates.toArray((new Predicate[]{})));
        });

    }

    //метод на получение всех task
    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    public Task add(Task task) {
        task.setCreateDate(new Date());
        task.setUpdateDate(null);
        return taskRepository.save(task);
    }

    public Task save(Task task, Long id) {
        Task saveTask = findById(id);
        if (task.getText() != null) {
            saveTask.setText(task.getText());
        }

        if (task.getIsComplete() != null) {
            saveTask.setIsComplete(task.getIsComplete());
        }

        saveTask.setUpdateDate(new Date());
        return taskRepository.save(saveTask);
    }
    public void delete(Long id) {
        Task task = findById(id);
        taskRepository.delete(task);
    }

    public Task addTag(Long idTask, Long idTag) {
        Tag tag = tagService.findById(idTag);
        Task task = findById(idTask);
        if (task.getText() == null) {
            task.setTags(new HashSet<>());
        }
        task.getTags().add(tag);

        return taskRepository.save(task);
    }

    public Task addSubtasks(Long idTask, Long idTag) {
        Tag tag = tagService.findById(idTag);
        Task task = findById(idTask);
        if (task.getText() == null) {
            task.setTags(new HashSet<>());
        }
        task.getTags().add(tag);

        return taskRepository.save(task);
    }

}
