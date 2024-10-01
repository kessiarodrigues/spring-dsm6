package com.kessia.todo.model.repository;

import com.kessia.todo.model.entity.Todo;
import com.kessia.todo.model.enums.TodoStatus;
import org.assertj.core.api.Assert;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TodoRepositoryTest {
    @Autowired
    TodoRepository repository;

    @Autowired
    TestEntityManager entityManager;

    @Test
    public void shouldCreateTodo(){
        Todo todo = createMockedTodo();
        Todo savedTodo = repository.save(todo);
        Assertions.assertThat(savedTodo).isNotNull();
        Assertions.assertThat(savedTodo).isInstanceOf(Todo.class);
    }

    @Test
    public void shouldFindAll(){
        List<Todo> todoList = repository.findAll();

        Assertions.assertThat(todoList).isNotNull();

    }

    @Test
    public void shouldFindById() {
        Todo todo = createMockedTodo();

        Todo insertedTodo = entityManager.persist(todo);

        Optional<Todo> matchedTodo= repository.findById(insertedTodo.getId());

        Assertions.assertThat(matchedTodo.isPresent()).isTrue();

    }

    @Test
    public void shouldReturnEmptyWhenFindByIdWithInvalidId() {
        Optional<Todo> matchedTodo = repository.findById(100L);

        Assertions.assertThat(matchedTodo.isEmpty()).isTrue();
    }

    @Test
    public void shouldUpdate() {
        Todo todo = createMockedTodo();

        Todo insertedTodo = entityManager.persist(todo);
        entityManager.detach(todo);
        Todo updatedInsertedTodo = Todo.builder().id(insertedTodo.getId()).status(TodoStatus.EM_ANDAMENTO).nome(todo.getNome()).descricao(todo.getDescricao()).observacoes(todo.getObservacoes()).build();

        Todo updatedTodo = repository.save(updatedInsertedTodo);

        Assertions.assertThat(insertedTodo.getId()).isEqualTo(
                updatedTodo.getId()
        );
        Assertions.assertThat(todo.getStatus()).isNotEqualTo(updatedTodo.getStatus());

    }
    
    @Test
    public void shouldDelete() {
        Todo todo = createMockedTodo();

        Todo insertedTodo = entityManager.persist(todo);


        repository.deleteById(insertedTodo.getId());

        Optional<Todo> deletedTodo = repository.findById(insertedTodo.getId());

        Assertions.assertThat(deletedTodo).isNotPresent();
    }
    
    public static Todo createMockedTodo() {
        return Todo
                .builder()
                .nome("Todo teste")
                .observacoes("obs")
                .status(TodoStatus.PENDENTE)
                .descricao("desc").criadoEm(LocalDateTime.now())
                .build();
    }
}
