package br.com.wszd.jboard.service;

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
       return (ArrayList<Person>) repository.findAll();
    }

//    public Person getPerson(Long id){
//        return repository.findAllById(id);
//    }


}
