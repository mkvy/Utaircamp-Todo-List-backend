package ru.utair.student2.todo.demo.exception;

public class NotFoundException extends RuntimeException{

    public NotFoundException(Long id) {
        super("Could not find object with id = " + id);
    }
}
