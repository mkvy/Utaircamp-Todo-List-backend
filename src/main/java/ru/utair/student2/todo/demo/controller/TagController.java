package ru.utair.student2.todo.demo.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.utair.student2.todo.demo.entity.Tag;
import ru.utair.student2.todo.demo.entity.Task;
import ru.utair.student2.todo.demo.exception.NotFoundException;
import ru.utair.student2.todo.demo.service.TagService;
import ru.utair.student2.todo.demo.service.TaskService;

import java.util.List;

@RestController
@RequestMapping("api/v1/tag")
@AllArgsConstructor
public class TagController {
    private final TagService tagService;


    private List<Tag> clear(List<Tag> tags) {
        tags.forEach(this::clear);
        return tags;
    }
    private Tag clear(Tag tag) {
        if (tag.getTaskSet() != null)
            tag.getTaskSet().forEach(task -> {
                task.setTags(null);
            });
        return tag;
    }


    @GetMapping
    List<Tag> findAll() {
        return clear(tagService.findAll());
    }

    @GetMapping("{id}")
    Tag findById(@PathVariable Long id) {
        return clear(tagService.findById(id));
    }

    @PostMapping
    Tag add(@RequestBody Tag tag) {
        return clear(tagService.add(tag));
    }

    @PutMapping("{id}")
    Tag save(@RequestBody Tag tag, @PathVariable Long id) {
        return clear(tagService.save(tag, id));
    }

    @DeleteMapping("{id}")
    void delete(@PathVariable Long id) {
        tagService.delete(id);
    }

}
