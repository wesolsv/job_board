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
import java.util.List;

@Service
@Slf4j
public class PersonService {

    @Autowired
    private PersonRepository repository;

    private BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    public List<PersonDTO> getAllPerson(){
        log.info("Buscando todas as pessoas");
       return repository.listarPerson();
    }

    public Person getPerson(Long id){
        log.info("Buscando pessoa");
        return repository.findById(id).orElseThrow(
                () ->  new ObjectNotFoundException("Não encontrado id = " + id));
    }

    public PersonDTO createNewPerson(Person novo) {
        log.info("Adicionando nova pessoa");

        if(repository.findByEmail(novo.getEmail()) != null || repository.findByCpf(novo.getCpf()) != null){
            throw new BadRequestException("Email ou CPF já cadastrado, verfique seus dados");
        }

        Person person = repository.save(new Person.Builder()
                .name(novo.getName())
                .phone(novo.getPhone().replaceAll("\\D", ""))
                .email(novo.getEmail())
                .cpf(novo.getCpf().replaceAll("\\D", ""))
                .password(passwordEncoder().encode(novo.getPassword()))
                .user(novo.getUser())
                .build());

        return new PersonDTO.Builder()
                .id(person.getId())
                .name(person.getName())
                .phone(person.getPhone())
                .email(person.getEmail())
                .cpf(person.getCpf())
                .build();
    }

    public PersonDTO editPerson(Long id, Person novo){
        log.info("Editando pessoa");
        getPerson(id);
        novo.setId(id);

        repository.save(novo);

        return new PersonDTO.Builder()
                .id(novo.getId())
                .name(novo.getName())
                .phone(novo.getPhone())
                .email(novo.getEmail())
                .cpf(novo.getCpf())
                .build();
    }

    public void deletePerson(Long id){
        log.info("Deletando pessoa");
        getPerson(id);
        repository.deleteById(id);
    }
}
