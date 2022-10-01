package br.com.wszd.jboard.controller;

import br.com.wszd.jboard.model.Candidacy;
import br.com.wszd.jboard.service.CandidacyService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("api/v1/candidacy")
public class CandidacyController {

    @Autowired
    private CandidacyService service;

    @ApiOperation(value = "Retorna todas as candidaturas")
    @GetMapping
    public ArrayList<Candidacy> getAllCandidacy(){
        return service.getAllCandidacy();
    }

    @ApiOperation(value = "Retorna uma candidatura")
    @GetMapping("/{id}")
    public ResponseEntity<Candidacy> getOneCandidacy(@PathVariable Long id){
        Candidacy res = service.getCandidacy(id);
        if(res != null){
            return ResponseEntity.ok(res);
        }
        return ResponseEntity.notFound().build();
    }
    @ApiOperation(value = "Cria nova candidatura")
    @PostMapping
    public ResponseEntity<Candidacy> createCandidacy(@RequestBody Candidacy novo){
        Candidacy res = service.createNewCandidacy(novo);
        if(res != null){
            return ResponseEntity.ok(res);
        }
        return ResponseEntity.notFound().build();
    }

    @ApiOperation(value = "Altera uma candidatura")
    @PutMapping
    public ResponseEntity<Candidacy> editCandidacy(@RequestBody Candidacy novo){
        Candidacy res = service.editCandidacy((novo));

        if(res != null){
            return ResponseEntity.ok(res);
        }
        return ResponseEntity.notFound().build();
    }

    @ApiOperation(value = "Deletando uma candidatura")
    @DeleteMapping("/{id}")
    public void deleteCandidacy(@PathVariable Long id){
        service.deleteCandidacy(id);
    }
}
