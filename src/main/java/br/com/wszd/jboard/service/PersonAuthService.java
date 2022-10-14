package br.com.wszd.jboard.service;

import br.com.wszd.jboard.dto.PersonDTO;
import br.com.wszd.jboard.exceptions.BadRequestException;
import br.com.wszd.jboard.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonAuthService {

    @Autowired
    private  PersonRepository personRepository;

    private TokenService tokenService;

    public PersonDTO authenticate(PersonDTO dados){
        PersonDTO person  = personRepository.findByEmail(dados.getEmail(), dados.getPassword());

        if(dados.getPassword().equals(person.getPassword())){
            String token = tokenService.genereteToken(person);
            System.out.println(token);
            return person;
        }else{
            throw new BadRequestException("deu ruim");
        }
    }
}
