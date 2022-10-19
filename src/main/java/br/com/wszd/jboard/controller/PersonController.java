package br.com.wszd.jboard.controller;

import br.com.wszd.jboard.dto.PersonCandidacyDTO;
import br.com.wszd.jboard.dto.PersonDTO;
import br.com.wszd.jboard.model.Candidacy;
import br.com.wszd.jboard.model.Person;
import br.com.wszd.jboard.service.CandidacyService;
import br.com.wszd.jboard.service.PersonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/v1/person")
@Api(value = "Person")
public class PersonController {

    @Autowired
    private PersonService service;

    @Autowired
    private CandidacyService candidacyService;

    @ApiOperation(value = "Retorna todas as pessoas")
    @GetMapping
    public List<PersonDTO> getAllPeople(){
        return service.getAllPerson();
    }

    @ApiOperation(value = "Retorna uma pessoa")
    @GetMapping("/{id}")
    public ResponseEntity<PersonDTO> getOnePerson(@PathVariable Long id){
        PersonDTO res = service.getPersonDTO(id);
        if(res != null){
            return ResponseEntity.ok(res);
        }
        return ResponseEntity.notFound().build();
    }
    @ApiOperation(value = "Cria nova pessoa")
    @PostMapping
    public ResponseEntity<PersonDTO> createPerson(@RequestBody Person novo){
        PersonDTO res = service.createNewPerson(novo);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(res.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @ApiOperation(value = "Altera uma pessoa")
    @PutMapping("/{id}")
    public ResponseEntity<PersonDTO> editPerson(@PathVariable Long id, @RequestBody Person novo){
        PersonDTO res = service.editPerson(id, novo);

        if(res != null){
            return ResponseEntity.ok(res);
        }
        return ResponseEntity.notFound().build();
    }

    @ApiOperation(value = "Deletando uma pessoa")
    @DeleteMapping("/{id}")
    public void deletePerson(@PathVariable Long id){
        service.deletePerson(id);
    }

    // Operação para realizar candidatura a uma vaga

    @ApiOperation(value = "Realizar candidatura a uma vaga")
    @PostMapping("/candidacy")
    public ResponseEntity<Candidacy> candidacyPerson(@RequestBody Candidacy candidacy){
        Candidacy cand = candidacyService.createNewCandidacy(candidacy);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(cand.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @ApiOperation(value = "Retorna todas as candidaturas da pessoa")
    @GetMapping("/candidacy/{id}")
    public ArrayList<PersonCandidacyDTO> allCandidacyByPersonId(@PathVariable Long id){
        return candidacyService.getAllCandidacyByPersonId(id);
    }
}
