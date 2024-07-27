package br.com.leonardo.tarefas.dto;

import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class UsuarioDTO {

    private Long id;

    private String nome;

    private String cpf;
}