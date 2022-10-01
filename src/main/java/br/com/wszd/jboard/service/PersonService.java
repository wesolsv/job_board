package br.com.wszd.jboard.service;

import br.com.wszd.jboard.exceptions.BadRequestException;
import br.com.wszd.jboard.exceptions.ObjectNotFoundException;
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
        log.info("Buscando todas as pessoas");
       return (ArrayList<Person>) repository.findAll();
    }

    public Person getPerson(Long id){
        log.info("Buscando pessoa");
        return repository.findById(id).orElseThrow(
                () ->  new ObjectNotFoundException("Objeto não encontrado com o id = " + id));
    }

    public Person createNewPerson(Person novo) {
        log.info("Adicionando nova pessoa");

        Person person = new Person.Builder()
                .name(novo.getName())
                .phone(novo.getPhone().replaceAll("\\D", ""))
                .email(novo.getEmail())
                .cpf(novo.getCpf().replaceAll("\\D", ""))
                .build();
        try{
            repository.save(person);
        }catch(BadRequestException e){
            throw new BadRequestException("Falha ao criar pessoa");
        }
        return person;
    }

    public Person editPerson(Person novo){
        log.info("Editando pessoa");
        getPerson(novo.getId());
        return repository.save(novo);
    }

    public void deletePerson(Long id){
        log.info("Deletando pessoa");
        getPerson(id);
        repository.deleteById(id);
    }
}
