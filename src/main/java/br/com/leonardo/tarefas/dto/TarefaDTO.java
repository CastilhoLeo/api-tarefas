package br.com.leonardo.tarefas.dto;

import br.com.leonardo.tarefas.enums.Situacao;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TarefaDTO {

    private Long id;

    private String titulo;

    private String descricao;

    private LocalDate dataVencimento;

    private Situacao situacao;

}
