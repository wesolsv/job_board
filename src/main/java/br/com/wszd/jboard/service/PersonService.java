package br.com.wszd.jboard.service;

import br.com.wszd.jboard.dto.PersonDTO;
import br.com.wszd.jboard.exceptions.BadRequestException;
import br.com.wszd.jboard.exceptions.ObjectNotFoundException;
import br.com.wszd.jboard.model.Person;
import br.com.wszd.jboard.repository.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@Slf4j
public class PersonService {

    @Autowired
    private PersonRepository repository;

    private BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    public ArrayList<Person> getAllPerson(){
        log.info("Buscando todas as pessoas");
       return (ArrayList<Person>) repository.findAll();
    }

    public Person getPerson(Long id){
        log.info("Buscando pessoa");
        return repository.findById(id).orElseThrow(
                () ->  new ObjectNotFoundException("Objeto não encontrado com o id = " + id));
    }

    public PersonDTO createNewPerson(Person novo) {
        log.info("Adicionando nova pessoa");

        if(repository.findByEmail(novo.getEmail()) != null || repository.findByCpf(novo.getCpf()) != null){
            throw new BadRequestException("Email ou CPF já cadastrado, verfique seus dados");
        }

        Person person = new Person.Builder()
                .name(novo.getName())
                .phone(novo.getPhone().replaceAll("\\D", ""))
                .email(novo.getEmail())
                .cpf(novo.getCpf().replaceAll("\\D", ""))
                .password(passwordEncoder().encode(novo.getPassword()))
                .roles(novo.getRoles())
                .build();

        repository.save(person);

        return new PersonDTO.Builder()
                .name(person.getName())
                .phone(person.getPhone())
                .email(person.getEmail())
                .cpf(person.getCpf())
                .build();
    }

    public Person editPerson(Long id, Person novo){
        log.info("Editando pessoa");
        getPerson(id);
        novo.setId(id);
        return repository.save(novo);
    }

    public void deletePerson(Long id){
        log.info("Deletando pessoa");
        getPerson(id);
        repository.deleteById(id);
    }
}
