package br.com.leonardo.tarefas.controller;

import br.com.leonardo.tarefas.dto.TarefaDTO;
import br.com.leonardo.tarefas.service.TarefaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("tarefas")
public class TarefaController {

    @Autowired
    private TarefaService tarefaService;

    @PostMapping()
    public ResponseEntity<TarefaDTO> cadastrarTarefa(@RequestBody @Valid TarefaDTO tarefaDTO){

        return ResponseEntity.ok()
                .body(tarefaService.cadastrarTarefa(tarefaDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TarefaDTO> editarTarefa(@PathVariable Long id, @RequestBody @Valid TarefaDTO tarefaDTO){
        return ResponseEntity.ok()
                .body(tarefaService.editarTarefa(id, tarefaDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity localizarPeloId(@PathVariable Long id){
        return ResponseEntity.ok().body(tarefaService.localizarPeloId(id));
    }


    @GetMapping
    public ResponseEntity<List<TarefaDTO>> localizarTodos(){
        return ResponseEntity.ok()
                .body(tarefaService.pesquisarTodos());
    }

    @DeleteMapping("{id}")
    public ResponseEntity deletarTarefa(@PathVariable Long id){

        tarefaService.deletarTarefa(id);
        return ResponseEntity.noContent().build();
    }

}
