package br.com.wszd.jboard.controller;

import br.com.wszd.jboard.model.Person;
import br.com.wszd.jboard.service.PersonService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("api/v1/person")
public class PersonController {

    @Autowired
    private PersonService service;

    @ApiOperation(value = "Retorna todas as pessoas")
    @GetMapping
    public ArrayList<Person> getAllPets(){
        return service.getAllPerson();
    }
}
