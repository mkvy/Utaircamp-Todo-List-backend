package ru.utair.student2.todo.demo.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

@Entity // в скобках можно () указать таблицу
//@JsonInclude(JsonInclude.Include.NON_EMPTY) //пустые поля не будут
@AllArgsConstructor//создает конструкторы
@NoArgsConstructor
@Data//нотация включает в себя equals,hash,setter,getter
@EqualsAndHashCode(exclude = "tags")
@Builder // конструктор, но поля становятся необязательными ставноятся
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) //autoincrement
    private Long id;
    private String text;
    private Boolean isComplete;
    private Date createDate;
    private Date updateDate;
    @ManyToMany
    @JoinTable(
            name = "task_tag",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags;

    private Long parentId;
    @OneToMany(mappedBy = "parentId")
    private Set<Task> subTasks;
}

