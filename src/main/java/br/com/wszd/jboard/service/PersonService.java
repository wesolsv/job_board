package br.com.wszd.jboard.service;

import br.com.wszd.jboard.model.exceptions.BadRequestException;
import br.com.wszd.jboard.model.exceptions.ObjectNotFoundException;
import br.com.wszd.jboard.model.Person;
import br.com.wszd.jboard.repository.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@Slf4j
public class PersonService {

    @Autowired
    private PersonRepository repository;

    public ArrayList<Person> getAllPerson(){
        log.info("Buscando todos os objetos person");
       return (ArrayList<Person>) repository.findAll();
    }

    public Person getPerson(Long id){
        log.info("Buscando objeto person");
        return repository.findById(id).orElseThrow(
                () ->  new ObjectNotFoundException("Objeto não encontrado com o id = " + id));
    }

    public Person createNewPerson(Person novo) {
        log.info("Adicionando novo objeto person");

        Person person = new Person.Builder()
                .name(novo.getName())
                .phone(novo.getPhone().replaceAll("\\D", ""))
                .email(novo.getEmail())
                .cpf(novo.getCpf().replaceAll("\\D", ""))
                .build();
        try{
            repository.save(person);
        }catch(BadRequestException e){
            throw new BadRequestException("Falha ao criar person");
        }
        return person;
    }
}
