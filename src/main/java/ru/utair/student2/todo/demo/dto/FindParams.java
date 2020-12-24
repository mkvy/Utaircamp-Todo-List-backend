package ru.utair.student2.todo.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import ru.utair.student2.todo.demo.entity.Tag;

import java.util.Date;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class FindParams {
    private String text;
    private Date afterCreateDate;
    private Date beforeCreateDate;
    private List<Tag> tags;
    private Boolean isComplete;

}
