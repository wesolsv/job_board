package br.com.wszd.jboard.controller;

import br.com.wszd.jboard.dto.CandidacyDTO;
import br.com.wszd.jboard.model.Candidacy;
import br.com.wszd.jboard.service.CandidacyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;

@RestController
@RequestMapping("api/v1/candidacy")
@Api(value = "Candidacy")
public class CandidacyController {

    @Autowired
    private CandidacyService service;

    @ApiOperation(value = "Retorna todas as candidaturas")
    @GetMapping
    public ArrayList<CandidacyDTO> getAllCandidacy(){
        return service.getAllCandidacy();
    }

    @ApiOperation(value = "Retorna uma candidatura")
    @GetMapping("/{id}")
    public ResponseEntity<CandidacyDTO> getOneCandidacy(@PathVariable Long id){
        CandidacyDTO res = service.getCandidacy(id);
        return ResponseEntity.ok(res);
    }
    @ApiOperation(value = "Cria nova candidatura")
    @PostMapping
    public ResponseEntity<Candidacy> createCandidacy(@RequestBody Candidacy novo){
        Candidacy res = service.createNewCandidacy(novo);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(res.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @ApiOperation(value = "Altera uma candidatura")
    @PutMapping("/{id}")
    public ResponseEntity<CandidacyDTO> editCandidacy(@PathVariable Long id, @RequestBody Candidacy novo){
        CandidacyDTO res = service.editCandidacy(id, novo);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(res.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @ApiOperation(value = "Deleta uma candidatura")
    @DeleteMapping("/{id}")
    public void deleteCandidacy(@PathVariable Long id){
        service.deleteCandidacy(id);
    }
}
