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

    @Temporal(TemporalType.DATE)
    private LocalDate dataCriacao;

    @Temporal(TemporalType.DATE)
    private LocalDate dataVencimento;

    @Enumerated(EnumType.ORDINAL)
    private Situacao situacao;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

}
