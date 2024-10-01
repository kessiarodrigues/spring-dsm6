package com.kessia.todo.service;

import com.kessia.todo.model.entity.Todo;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface TodoService {
    void create(Todo newTodo);

    List<Todo> get();

    void update(Todo todo);

    void delete(Long id);

    Optional<Todo> getTodoById(Long id);
}
