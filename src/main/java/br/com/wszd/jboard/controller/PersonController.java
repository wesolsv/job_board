package br.com.wszd.jboard.controller;

import br.com.wszd.jboard.model.Person;
import br.com.wszd.jboard.service.PersonService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("api/v1/person")
public class PersonController {

    @Autowired
    private PersonService service;

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

    @PostMapping
    public ResponseEntity<Person> createPerson(@RequestBody Person novo){
        Person res = service.createNewPerson(novo);
        if(res != null){
            return ResponseEntity.ok(res);
        }
        return ResponseEntity.notFound().build();
    }
}
