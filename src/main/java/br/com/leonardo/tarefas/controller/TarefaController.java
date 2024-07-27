package br.com.leonardo.tarefas.controller;

import br.com.leonardo.tarefas.dto.TarefaDTO;
import br.com.leonardo.tarefas.service.TarefaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/tarefas")
public class TarefaController {

    @Autowired
    private TarefaService tarefaService;

    @PostMapping
    public ResponseEntity<TarefaDTO> cadastrarTarefa(@RequestBody TarefaDTO tarefaDTO){

        return ResponseEntity.ok()
                .body(tarefaService.cadastrarTarefa(tarefaDTO));
    }

    @PutMapping("/cadastro/{id}")
    public ResponseEntity<TarefaDTO> editarTarefa(@PathVariable Long id, @RequestBody TarefaDTO tarefaDTO){
        return ResponseEntity.ok().body(tarefaService.editarTarefa(id, tarefaDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity localizarPeloId(@PathVariable Long id){
        return ResponseEntity.ok().build();
    }
}
