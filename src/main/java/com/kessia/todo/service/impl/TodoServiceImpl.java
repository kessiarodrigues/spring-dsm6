package com.kessia.todo.service.impl;

import com.kessia.todo.model.entity.Todo;
import com.kessia.todo.model.repository.TodoRepository;
import com.kessia.todo.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TodoServiceImpl implements TodoService {
    private TodoRepository repository;

    @Autowired
    public TodoServiceImpl(TodoRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Todo> get(){
        return repository.findAll();
    }

    @Override
    public void create(Todo newTodo) {
        repository.save(newTodo);

        return;
    }

    @Override
    public void update( Todo updatedTodo){
        repository.save(updatedTodo);

        return;
    }

    @Override
    public void delete(Long id){
        repository.deleteById(id);

        return;
    }

    @Override
    public Optional<Todo> getTodoById(Long id){
        return repository.findById(id);
    }
}
