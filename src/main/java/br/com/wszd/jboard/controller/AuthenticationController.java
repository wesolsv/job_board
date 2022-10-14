package br.com.wszd.jboard.controller;

import br.com.wszd.jboard.dto.PersonDTO;
import br.com.wszd.jboard.model.Person;
import br.com.wszd.jboard.service.PersonAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/")
public class AuthenticationController {

    @Autowired
    private PersonAuthService personAuthService;

    @PostMapping("person/login")
    public ResponseEntity<PersonDTO> autentitcar(@RequestBody PersonDTO dados){
        PersonDTO person = personAuthService.authenticate(dados);

        return ResponseEntity.ok(person);
    }


}
