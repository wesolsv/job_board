package br.com.wszd.jboard.controller;

import br.com.wszd.jboard.dto.PersonCandidacyDTO;
import br.com.wszd.jboard.model.Candidacy;
import br.com.wszd.jboard.model.Person;
import br.com.wszd.jboard.service.CandidacyService;
import br.com.wszd.jboard.service.PersonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

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
    public ArrayList<Person> getAllPeople(){
        return service.getAllPerson();
    }

    @ApiOperation(value = "Retorna uma pessoa")
    @GetMapping("/{id}")
    public ResponseEntity<Person> getOnePerson(@PathVariable Long id){
        Person res = service.getPerson(id);
        if(res != null){
            return ResponseEntity.ok(res);
        }
        return ResponseEntity.notFound().build();
    }
    @ApiOperation(value = "Cria nova pessoa")
    @PostMapping
    public ResponseEntity<Person> createPerson(@RequestBody Person novo){
        Person res = service.createNewPerson(novo);
        if(res != null){
            return ResponseEntity.ok(res);
        }
        return ResponseEntity.badRequest().build();
    }

    @ApiOperation(value = "Altera uma pessoa")
    @PutMapping("/{id}")
    public ResponseEntity<Person> editPerson(@PathVariable Long id, @RequestBody Person novo){
        Person res = service.editPerson(id, novo);

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
        if(cand != null){
            return ResponseEntity.ok(cand);
        }

        return ResponseEntity.badRequest().build();
    }

    @ApiOperation(value = "Pegar todas as candidaturas por pessoa")
    @GetMapping("/candidacy/{id}")
    public ArrayList<PersonCandidacyDTO> allCandidacyByPersonId(@PathVariable Long id){
        return candidacyService.getAllCandidacyByPersonId(id);
    }
}
