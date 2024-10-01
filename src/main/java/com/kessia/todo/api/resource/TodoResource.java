package com.kessia.todo.api.resource;

import com.kessia.todo.api.dto.TodoDto;
import com.kessia.todo.model.entity.Todo;
import com.kessia.todo.service.TodoService;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/todo")
public class TodoResource {
    private TodoService service;

    public TodoResource(TodoService service) {
        this.service = service;
    }

    @GetMapping()
    public ResponseEntity<Object> getTodos (){
        try{
            List<Todo> todoList = service.get();
            return ResponseEntity.ok(todoList);
        }catch(Exception e ){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PostMapping()
    public ResponseEntity<Object> create (@RequestBody TodoDto todo){

        Todo newTodo = Todo
                .builder()
                .nome(todo.getNome())
                .status(todo.getStatus())
                .descricao(todo.getDescricao())
                .observacoes(todo.getObservacoes()).build();
        try{
            service.create(newTodo);
            return ResponseEntity.status(HttpStatusCode.valueOf(201)).build();
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> update(@PathVariable("id") Long id, @RequestBody TodoDto todo){
        Todo newTodo = Todo
                .builder()
                .id(id)
                .nome(todo.getNome())
                .status(todo.getStatus())
                .descricao(todo.getDescricao())
                .observacoes(todo.getObservacoes()).build();
        try{
            Optional<Todo> hasTodo = service.getTodoById(id);
            if(hasTodo.isEmpty()){
                return ResponseEntity.badRequest().body("Não foi encontrado este todo");
               }
            service.update(newTodo);
            return ResponseEntity.ok().build();
        }catch(Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") Long id){
        try{
            Optional<Todo> hasTodo = service.getTodoById(id);
            if(hasTodo.isEmpty()){
                return ResponseEntity.badRequest().body("Não foi encontrado este todo");
            }
            service.delete(id);

            return ResponseEntity.ok().build();
        }catch(Exception e ){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

}
