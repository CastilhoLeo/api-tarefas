package br.com.leonardo.tarefas.entity;

import br.com.leonardo.tarefas.enums.Situacao;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Tarefa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    private String titulo;

    private String descricao;

    @Column(name = "data_criacao")
    @Temporal(TemporalType.DATE)
    private LocalDate dataCriacao;

    @Column(name = "data_vencimento")
    @Temporal(TemporalType.DATE)
    private LocalDate dataVencimento;

    @Enumerated(EnumType.ORDINAL)
    private Situacao situacao;


}
