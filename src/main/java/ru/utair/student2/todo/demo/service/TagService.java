package ru.utair.student2.todo.demo.service;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.utair.student2.todo.demo.entity.Tag;
import ru.utair.student2.todo.demo.entity.Task;
import ru.utair.student2.todo.demo.exception.NotFoundException;
import ru.utair.student2.todo.demo.repository.TagRepository;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class TagService {
    private final TagRepository tagRepository;

    public Tag findById(Long id) {
        return tagRepository.findById(id).orElseThrow(() -> new NotFoundException(id));

    }

    public List<Tag> findAll() {
        return tagRepository.findAll();
    }

    public Tag add(Tag tag) {
        return tagRepository.save(tag);
    }

    public void delete(Long id) {

        if (tagRepository.existsById(id)) {
            tagRepository.deleteById(id);
        }
        else
        {
            throw new NotFoundException(id);
        }
    }

    public Tag save(Tag tag, Long id) {
        Tag tagSave = findById(id);
        if (tag.getText() != null) {
            tagSave.setText(tag.getText());
        }
        return tagRepository.save(tagSave);
    }


}
