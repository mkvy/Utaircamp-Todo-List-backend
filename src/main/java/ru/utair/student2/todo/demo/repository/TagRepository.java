package ru.utair.student2.todo.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.utair.student2.todo.demo.entity.Tag;

@Repository
public interface TagRepository extends JpaRepository<Tag,Long> {


}
