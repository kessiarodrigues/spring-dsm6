package com.kessia.todo.api.dto;

import com.kessia.todo.model.enums.TodoStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TodoDto {
    private String nome;
    private String descricao;
    private TodoStatus status;
    private String observacoes;
}
